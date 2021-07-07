package top.meethigher.springstatemachine.entity;


import top.meethigher.springstatemachine.enums.OrderState;

/**
 *
 * @author chenchuancheng
 * @date 2021-07-07 14:40:22
 **/
public class Order {
    private String id;
    private OrderState state;

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", state=" + state +
                '}';
    }
}
