package top.meethigher.springstatemachine.repository;


import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import top.meethigher.springstatemachine.entity.Order;
import top.meethigher.springstatemachine.utils.OrderIdGenerator;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 模拟与数据库的交互
 * @author chenchuancheng
 * @date 2021-07-07 16:59:17
 **/
@Repository
public class OrderRepository {
    @Resource
    private OrderIdGenerator generator;
    private static Map<String, Order> database=new HashMap<>();

    /**
     * 通过id查询
     * @param id
     * @return
     */
    public Order findById(String id){
        return database.get(id);
    }

    /**
     * 保存入库操作
     * @param order
     * @return
     */
    public Order save(Order order){
        if(ObjectUtils.isEmpty(order.getId())){
            order.setId(generator.createId());
        }
        database.put(order.getId(),order);
        return order;
    }

}
