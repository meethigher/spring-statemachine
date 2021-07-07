package top.meethigher.springstatemachine.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import top.meethigher.springstatemachine.enums.OrderEvent;
import top.meethigher.springstatemachine.enums.OrderState;

import java.util.EnumSet;

@Configuration
@EnableStateMachine(name="orderStateMachine")
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderState, OrderEvent> {
    public OrderStateMachineConfig() {

    }

    /**
     * 配置状态
     *
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> states) throws Exception {
        states.withStates()
                .initial(OrderState.WAIT_PAY)
                .states(EnumSet.allOf(OrderState.class));
    }

    /**
     * 配置转换关系
     *
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions) throws Exception {
        transitions
                .withExternal().source(OrderState.WAIT_PAY).target(OrderState.WAIT_DELIVER).event(OrderEvent.PAY)
                .and()
                .withExternal().source(OrderState.WAIT_DELIVER).target(OrderState.WAIT_RECEIVE).event(OrderEvent.DELIVER)
                .and()
                .withExternal().source(OrderState.WAIT_RECEIVE).target(OrderState.FINISH).event(OrderEvent.RECEIVE);

    }
}
