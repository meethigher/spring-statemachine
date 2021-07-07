并没有深入学习，根据网上一些搜到的demo简单上手写了一下

Spring Statemachine 是应用程序开发人员在 Spring 应用程序中使用状态机概念的框架。

<!--more-->

参考demo

1. [sunbufu的源码(主要还是参照这个大佬的源码和博客)](https://github.com/sunbufu/spring-state-machine-demo)
2. [sunbufu的博客](https://sunbufu.vercel.app/posts/2018-and-before/2018-06-13-spring-statemachine/)
3. [qq](https://github.com/QQsilhonette/SpringStateMachineDemo)

案例流程图如下所示，[本案例源码](https://gitee.com/meethigher/spring-statemachine)

![](https://meethigher.top/blog/2021/spring-statemachine/1.png)

下面放置关键代码

OrderStateMachineConfig

```java
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
```

OrderStateMachinePersisterConfig

```java
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
```

OrderStateMachineHandler

```java
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
```

OrderStateMachineListener

```java
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
```

