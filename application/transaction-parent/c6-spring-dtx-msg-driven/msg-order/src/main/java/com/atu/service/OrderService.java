package com.atu.service;

import com.atu.dao.OrderRepository;
import com.atu.domain.Order;
import com.atu.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class OrderService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private OrderRepository orderRepository;

    /**
     * 创建订单
     *
     * @param msg
     */
    @Transactional
    @JmsListener(destination = "order:locked", containerFactory = "msgFactory")
    public void handle(OrderDTO msg) {
        LOG.info("Get new order to create:{}", msg);
        //判断是否重复处理
        if (orderRepository.findOneByUuid(msg.getUuid()) != null) { // 通过保存到数据库，来使用uuid处理重复消息
            LOG.info("Msg already processed:{}", msg);
        } else {
            Order order = newOrder(msg);
            orderRepository.save(order);
            msg.setId(order.getId());
        }
        msg.setStatus("NEW");
        //发送到代缴费队列
        jmsTemplate.convertAndSend("order:pay", msg);
    }

    private Order newOrder(OrderDTO dto) {
        Order order = new Order();
        order.setUuid(dto.getUuid());
        order.setAmount(dto.getAmount());
        order.setTitle(dto.getTitle());
        order.setCustomerId(dto.getCustomerId());
        order.setTicketNum(dto.getTicketNum());
        order.setStatus("NEW");
        order.setCreatedDate(ZonedDateTime.now());
        return order;
    }

    /**
     * 订单完成
     *
     * @param msg
     */
    @Transactional
    @JmsListener(destination = "order:finish", containerFactory = "msgFactory")
    public void handleFinish(OrderDTO msg) {
        LOG.info("Get finished order:{}", msg);
        Order order = orderRepository.findOne(msg.getId());
        order.setStatus("FINISH");
        orderRepository.save(order);
    }

    /**
     * 订单失败的几种情况：
     * 1. 一开始锁票失败;
     * 2. 扣费失败后，解锁票，然后触发
     * 3. 定时任务检测到订单超时
     *
     * @param msg
     */
    @Transactional
    @JmsListener(destination = "order:fail", containerFactory = "msgFactory")
    public void handleFailed(OrderDTO msg) {
        LOG.info("Get failed order:{}", msg);
        Order order;
        if (msg.getId() == null) {
            order = newOrder(msg);
            order.setReason("TICKET_LOCK_FAIL"); // 锁票失败，可能票id不对，或者已被别人买走
        } else {
            //余额不足
            order = orderRepository.findOne(msg.getId());
            if (msg.getStatus().equals("NOT_ENOUGH_DEPOSIT")) {
                order.setReason("NOT_ENOUGH_DEPOSIT");
            }
        }
        order.setStatus("FAIL");
        orderRepository.save(order);
    }

    /**
     * 定时任务
     */
    @Scheduled(fixedDelay = 10000L)
    public void checkInvalidOrder() {
        ZonedDateTime checkTime = ZonedDateTime.now().minusMinutes(1L);
        List<Order> orders = orderRepository.findAllByStatusAndCreatedDateBefore("NEW", checkTime);
        orders.stream().forEach(order -> {
            LOG.error("Order timeout:{}", order);
            OrderDTO dto = new OrderDTO();
            dto.setId(order.getId());
            dto.setTicketNum(order.getTicketNum());
            dto.setUuid(order.getUuid());
            dto.setAmount(order.getAmount());
            dto.setTitle(order.getTitle());
            dto.setCustomerId(order.getCustomerId());
            dto.setStatus("TIMEOUT");
            //消息机制处理
            jmsTemplate.convertAndSend("order:ticket_error", dto);
        });
    }
}
