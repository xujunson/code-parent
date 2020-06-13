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
}
