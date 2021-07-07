package top.meethigher.springstatemachine.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.meethigher.springstatemachine.entity.Order;
import top.meethigher.springstatemachine.enums.OrderEvent;
import top.meethigher.springstatemachine.enums.OrderState;
import top.meethigher.springstatemachine.handler.OrderStateMachineHandler;
import top.meethigher.springstatemachine.repository.OrderRepository;
import top.meethigher.springstatemachine.service.OrderService;
import top.meethigher.springstatemachine.utils.OrderIdGenerator;

import javax.annotation.Resource;

/**
 * @author chenchuancheng
 * @date 2021-07-07 16:13:31
 **/
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log=LoggerFactory.getLogger(OrderServiceImpl.class);
    @Resource
    private OrderIdGenerator generator;
    @Resource
    private OrderStateMachineHandler handler;
    @Resource
    private OrderRepository repository;

    @Override
    public Order create() {
        Order order = new Order();
        String id = generator.createId();
        order.setId(id);
        order.setState(OrderState.WAIT_PAY);
        log.info("创建了订单========="+order);
        return repository.save(order);
    }

    @Override
    public Order pay(String id) {
        Order order = repository.findById(id);
        boolean result = handler.sendEvent(OrderEvent.PAY, order);
        if (!result) {
            throw new RuntimeException(OrderEvent.PAY + "失败，订单号=" + order.getId());
        }
        log.info("支付了订单========="+order);
        return order;
    }

    @Override
    public Order deliver(String id) {
        Order order = repository.findById(id);
        boolean result = handler.sendEvent(OrderEvent.DELIVER, order);
        if (!result) {
            throw new RuntimeException(OrderEvent.DELIVER + "失败，订单号=" + order.getId());
        }
        log.info("邮寄了订单========="+order);
        return order;
    }

    @Override
    public Order receive(String id) {
        Order order = repository.findById(id);
        boolean result = handler.sendEvent(OrderEvent.RECEIVE, order);
        if (!result) {
            throw new RuntimeException(OrderEvent.RECEIVE + "失败，订单号=" + order.getId());
        }
        log.info("签收了订单========="+order);
        return order;
    }
}
