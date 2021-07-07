package top.meethigher.springstatemachine.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import top.meethigher.springstatemachine.entity.Order;
import top.meethigher.springstatemachine.enums.OrderEvent;
import top.meethigher.springstatemachine.enums.OrderState;

/**
 * OrderStateMachinePersisterConfig
 *
 * @author kit chen
 * @github https://github.com/meethigher
 * @blog https://meethigher.top
 * @time 2021/7/7
 */
@Configuration
public class OrderStateMachinePersisterConfig {
    @Bean
    public StateMachinePersister<OrderState, OrderEvent, Order> orderStateMachinePersist() {
        return new DefaultStateMachinePersister<>(new StateMachinePersist<OrderState, OrderEvent, Order>() {
            @Override
            public StateMachineContext<OrderState, OrderEvent> read(Order order) {
                return new DefaultStateMachineContext<>(order.getState(), null, null, null);
            }

            @Override
            public void write(StateMachineContext<OrderState, OrderEvent> context, Order order) throws Exception {
                order.setState(context.getState());
            }
        });
    }
}
