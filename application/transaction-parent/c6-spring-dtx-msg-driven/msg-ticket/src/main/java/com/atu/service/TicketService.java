package com.atu.service;

import com.atu.dao.TicketRepository;
import com.atu.domain.Ticket;
import com.atu.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {

    private static final Logger LOG = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private TicketRepository ticketRepository;

    /**
     * 锁票
     * JmsListener 监听 destination
     *
     * @param msg
     */
    @JmsListener(destination = "order:new", containerFactory = "msgFactory")
    @Transactional
    public void handleTicketLock(OrderDTO msg) {
        LOG.info("Get new order for ticket lock:{}", msg);
        int lockCount = ticketRepository.lockTicket(msg.getCustomerId(), msg.getTicketNum());
        if (lockCount == 0) {
            //锁票失败
            msg.setStatus("TICKET_LOCK_FAIL");
            jmsTemplate.convertAndSend("order:fail", msg);
        } else {
            //锁票成功

            //设置状态
            msg.setStatus("TICKET_LOCKED");

            //创建订单
            jmsTemplate.convertAndSend("order:locked", msg);
        }
    }

    /**
     * 交票
     *
     * @param msg
     */
    @Transactional
    @JmsListener(destination = "order:ticket_move", containerFactory = "msgFactory")
    public void handleTicketMove(OrderDTO msg) {
        LOG.info("Get new order for ticket move:{}", msg);
        int moveCount = ticketRepository.moveTicket(msg.getCustomerId(), msg.getTicketNum());
        if (moveCount == 0) {
            LOG.info("Ticket already transferred.");
        }
        msg.setStatus("TICKET_MOVED");
        jmsTemplate.convertAndSend("order:finish", msg);
    }

    /**
     * 触发 error_ticket 的情况：
     * 1. 扣费失败，需要解锁票
     * 2. 订单超时，如果存在锁票就解锁，如果已经交票就撤回
     * 这时候，都已经在OrderDTO里设置了失败的原因，所以这里就不再设置原因。
     *
     * @param msg
     */
    @Transactional
    @JmsListener(destination = "order:ticket_error", containerFactory = "msgFactory")
    public void handleError(OrderDTO msg) {
        LOG.info("Get order error for ticket unlock:{}", msg);
        //撤回
        int count = ticketRepository.unMoveTicket(msg.getCustomerId(), msg.getTicketNum()); // 撤销票的转移
        if (count == 0) {
            LOG.info("Ticket already unmoved, or not moved:", msg);
        }

        //解锁
        count = ticketRepository.unLockTicket(msg.getCustomerId(), msg.getTicketNum()); // 撤销锁票
        if (count == 0) {
            LOG.info("Ticket already unlocked:", msg);
        }
        jmsTemplate.convertAndSend("order:fail", msg);
    }

    /**
     * 锁票方式1：获取对象修改保存
     * 存在问题:
     * 测试两个用户同时锁票 后触发的会覆盖前一个
     * 原因：springData底层用的jpa是hibernate，有一个二级缓存
     * ticketRepository.save()执行后，并没有保存到数据库里，
     * 而是在persistent context里面，这样的话先保存之后第二个就会把第一个覆盖掉
     * 解决：使用分布式锁
     *
     * @param orderDTO
     * @return
     */
    @Transactional
    public Ticket lockTicket(OrderDTO orderDTO) {
        //取票
        Ticket ticket = ticketRepository.findOneByTicketNum(orderDTO.getTicketNum());
        ticket.setLockUser(orderDTO.getCustomerId());
        ticket = ticketRepository.save(ticket);
        try {
            //模拟并发
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        }
        return ticket;
    }

    /**
     * 使用数据库事务实现过程中的锁 实现锁票 有多个实例都可以满足
     * 锁票 方式2
     * 实现第一个用户进来就可以锁住，第二个用户不会再锁住
     *
     * @param orderDTO
     * @return
     */
    @Transactional
    public int lockTicket2(OrderDTO orderDTO) {
        int updateCount = ticketRepository.lockTicket(orderDTO.getCustomerId(), orderDTO.getTicketNum());
        LOG.info("Updated ticket count:{}", updateCount);
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        }
        return updateCount;
    }

    /**
     * 解锁
     *
     * @param orderDTO
     * @return
     */
    @Transactional
    public int unLockTicket(OrderDTO orderDTO) {
        int updateCount = ticketRepository.unLockTicket(orderDTO.getCustomerId(), orderDTO.getTicketNum());
        LOG.info("Updated ticket count:{}", updateCount);
        return updateCount;
    }
}
