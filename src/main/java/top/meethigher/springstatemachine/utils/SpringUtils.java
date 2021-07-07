package top.meethigher.springstatemachine.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils {
    @Bean
    OrderIdGenerator getIdGenerator(){
        return new OrderIdGenerator();
    }
}
