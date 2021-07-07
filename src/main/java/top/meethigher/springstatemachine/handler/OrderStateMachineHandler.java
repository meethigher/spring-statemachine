package top.meethigher.springstatemachine.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;
import top.meethigher.springstatemachine.entity.Order;
import top.meethigher.springstatemachine.enums.OrderEvent;
import top.meethigher.springstatemachine.enums.OrderState;

/**
 * @author chenchuancheng
 * @date 2021-07-07 17:12:01
 **/
@Component
public class OrderStateMachineHandler {
    @Autowired
    private StateMachine<OrderState, OrderEvent> stateMachine;
    @Autowired
    private StateMachinePersister<OrderState, OrderEvent, Order> stateMachinePersister;

    public synchronized boolean sendEvent(OrderEvent event, Order order) {
        boolean result = false;
        try {
            stateMachine.start();
            //设置状态机状态
            stateMachinePersister.restore(stateMachine, order);
            Message<OrderEvent> eventMessage = MessageBuilder.withPayload(event).setHeader("order", order).build();
            result = stateMachine.sendEvent(eventMessage);
            //保存状态机状态
            stateMachinePersister.persist(stateMachine, order);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stateMachine.stop();
        }
        return result;
    }
}
