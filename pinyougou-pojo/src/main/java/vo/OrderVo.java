package vo;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;

import java.util.List;

public class OrderVo {
    private Order order;
    private List<OrderItem>  orderItemList;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    @Override
    public String toString() {
        return "OrderVo{" +
                "order=" + order +
                ", orderItemList=" + orderItemList +
                '}';
    }
}
