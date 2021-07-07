package top.meethigher.springstatemachine.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;
import top.meethigher.springstatemachine.enums.OrderEvent;

/**
 * @author chenchuancheng
 * @date 2021-07-07 15:32:06
 **/
@Component
@WithStateMachine(name = "orderStateMachine")
public class OrderStateMachineListener {
    private final static Logger log= LoggerFactory.getLogger(OrderStateMachineListener.class);
    @OnTransition(source = "WAIT_PAY", target = "WAIT_DELIVER")
    public boolean pay(Message<OrderEvent> message) {
        OrderEvent payload = message.getPayload();
        String name = payload.name();
        log.info("完成动作---"+name);
        return true;
    }

    @OnTransition(source = "WAIT_DELIVER", target = "WAIT_RECEIVE")
    public boolean deliver(Message<OrderEvent> message) {
        OrderEvent payload = message.getPayload();
        String name = payload.name();
        log.info("完成动作---"+name);
        return true;
    }

    @OnTransition(source = "WAIT_RECEIVE", target = "FINISH")
    public boolean receive(Message<OrderEvent> message) {
        OrderEvent payload = message.getPayload();
        String name = payload.name();
        log.info("完成动作---"+name);
        return true;
    }
}
