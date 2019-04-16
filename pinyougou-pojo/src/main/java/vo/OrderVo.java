package vo;

import cn.itcast.core.pojo.order.OrderItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单包装对象
 */
public class OrderVo implements Serializable {
    //订单创建时间
    private Date create_time;
    //订单编号
    private Long order_id;
    //订单编号
    private String order_id_str;
    //店铺名称
    private String nick_name;
    //订单项集合
    private List<OrderItem> orderItemList;
    //实付金额
    private BigDecimal payment;
    //状态
    private String status;

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public String getOrder_id_str() {
        return order_id_str;
    }

    public void setOrder_id_str(String order_id_str) {
        this.order_id_str = order_id_str;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
