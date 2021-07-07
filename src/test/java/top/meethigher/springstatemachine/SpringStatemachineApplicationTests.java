package top.meethigher.springstatemachine;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.meethigher.springstatemachine.entity.Order;
import top.meethigher.springstatemachine.service.OrderService;

import javax.annotation.Resource;

@SpringBootTest
class SpringStatemachineApplicationTests {

    @Resource
    private OrderService service;
    @Test
    void testBuy() {
        Order order = service.create();
        service.pay(order.getId());
        service.deliver(order.getId());
        service.receive(order.getId());
    }

}
