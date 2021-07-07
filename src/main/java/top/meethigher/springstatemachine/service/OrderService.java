package top.meethigher.springstatemachine.service;

import top.meethigher.springstatemachine.entity.Order;

/**
 * @author chenchuancheng
 * @date 2021-07-07 16:10:43
 **/
public interface OrderService {
    /**
     * 创建订单
     * @return
     */
    Order create();

    /**
     * 支付订单
     * @param id
     * @return
     */
    Order pay(String id);

    /**
     * 邮寄订单
     * @param id
     * @return
     */
    Order deliver(String id);

    /**
     * 签收订单
     * @param id
     * @return
     */
    Order receive(String id);
}
