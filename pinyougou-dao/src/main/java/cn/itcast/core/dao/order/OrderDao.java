package cn.itcast.core.dao.order;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import entity.OrderCount;
import org.apache.ibatis.annotations.Param;

public interface OrderDao {
    int countByExample(OrderQuery example);

    int deleteByExample(OrderQuery example);

    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderQuery example);

    Order selectByPrimaryKey(Long orderId);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderQuery example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderQuery example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    /**
     *  <select id="countSalesByDay" resultType="java.lang.Double">

     SELECT sum(payment)
     FROM tb_order
     <where>
     status IN ('1','2','3','4') AND

     <![CDATA[   and DATE_FORMAT(create_time, '%Y-%m-%d %H:%i:%S')>=  DATE_FORMAT(#{param1}, '%Y-%m-%d %H:%i:%S')   ]]>
     <![CDATA[  and DATE_FORMAT(create_time, '%Y-%m-%d %H:%i:%S') <= DATE_FORMAT(#{param2}, '%Y-%m-%d %H:%i:%S')    ]]>

     </where>
     </select>
     * @param s
     * @param s1
     * @return
     */
    Double countSalesByDay(String s, String s1);

    List<Map<String,Object>> findDaySalesGroupBySellerId(String s, String s1);

    List<Order> selectSome(OrderQuery example);

    Map findDaySalesBySellerId(String point, String point1, String name);

    List<OrderCount> countOrder(Map<String,String> map);
}