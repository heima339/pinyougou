package cn.itcast.core.service;

import cn.itcast.common.utils.DateUtils;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

@SuppressWarnings("all")
@Service
@Transactional
public class OrderCountServiceImpl implements OrderCountService {

    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public PageResult search(Integer page, Integer rows, OrderItem orderItem) {

        try {

            //分页插件
            PageHelper.startPage(page, rows);

            //条件 对象
            OrderQuery orderQuery = new OrderQuery();
            OrderQuery.Criteria criteria = orderQuery.createCriteria();


            //判断 状态  运营商后台 默认查询所有状态
            /*if (null != orderItem.getDateStatus() && !"".equals(orderItem.getDateStatus())) {
                criteria.andStatusEqualTo(orderItem.getDateStatus());
            }*/

            //判断当前查询的是商家后台 还是运营商后台
            if (null != orderItem.getSellerId()) {
                //商家查询
                //只能查询自己家的商品
                criteria.andSellerIdEqualTo(orderItem.getSellerId());
            }

            if ("".equals(orderItem.getDateStatus())) {

                //获取当前商家当日成交订单
                criteria.andSellerIdEqualTo(orderItem.getSellerId());

            }
            if ("0".equals(orderItem.getDateStatus())) {

                //日销售额
                String[] dayDate = DateUtils.getDayStartAndEndTimePointStr(new Date());

                Date dayStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dayDate[0]);
                Date dayEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dayDate[1]);

                //获取当前商家当日成交订单
                criteria.andCreateTimeBetween(dayStart, dayEnd);


            }
            if ("1".equals(orderItem.getDateStatus())) {

                //周销售额
                Date[] weekDate = DateUtils.getWeekStartAndEndDate(new Date());

                //获取当前商家当周成交订单
                criteria.andCreateTimeBetween(weekDate[0], weekDate[1]);


            }
            if ("2".equals(orderItem.getDateStatus())) {

                //月销售额
                Date[] monthDate = DateUtils.getMonthStartAndEndDate(new Date());

                //获取当前商家当月成交订单
                criteria.andCreateTimeBetween(monthDate[0], monthDate[1]);


            }
            if (null != orderItem.getSearchDate()&&!"".equals(orderItem.getSearchDate())) {
                Date date = new Date();
                //搜索日期销售额
                date = new SimpleDateFormat("yyyy-MM-dd").parse(orderItem.getSearchDate());

                String[] dayDate = DateUtils.getDayStartAndEndTimePointStr(date);
                Date dayStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dayDate[0]);
                Date dayEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dayDate[1]);

                //获取当前商家当日成交订单
                criteria.andCreateTimeBetween(dayStart, dayEnd);


            }

            Page<Order> orders = (Page<Order>) orderDao.selectByExample(orderQuery);

            Page<OrderItem> orderItemList = new Page<>();
            //遍历订单集合
            for (Order order : orders) {
                //获取订单id
                Long orderId = order.getOrderId();
                //获取订单项
                OrderItemQuery orderItemQuery = new OrderItemQuery();
                orderItemQuery.createCriteria().andOrderIdEqualTo(orderId);
                List<OrderItem> orderItems = orderItemDao.selectByExample(orderItemQuery);
                for (OrderItem newOrderItem : orderItems) {
                    //2)判断当前新商品 在上面的商品集合中是否已经存在
                    int indexOf = orderItemList.indexOf(newOrderItem);
                    if (indexOf != -1) {
                        //---存在
                        //找出对应的相同商品 追加金额
                        OrderItem oldOrderItem = orderItemList.get(indexOf);
                        oldOrderItem.setNum(oldOrderItem.getNum() + newOrderItem.getNum());
                        oldOrderItem.setTotalFee(BigDecimal.valueOf(oldOrderItem.getTotalFee().doubleValue() + newOrderItem.getTotalFee().doubleValue()));
                    } else {
                        //---不存在
                        //直接添加新商品即可
                        orderItemList.add(newOrderItem);
                    }
                }
            }

            return new PageResult(orderItemList.getTotal(), orderItemList.getResult());


        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

   /* public PageResult getOrderItemList(List<OrderItem> orderItemList, List<Order> orders) {

        if (null != orders && orders.size() > 0) {
            //遍历订单集合
            for (Order order : orders) {
                //获取订单id
                Long orderId = order.getOrderId();
                //获取订单项
                OrderItemQuery orderItemQuery = new OrderItemQuery();
                orderItemQuery.createCriteria().andOrderIdEqualTo(orderId);
                List<OrderItem> orderItems = orderItemDao.selectByExample(orderItemQuery);

                for (OrderItem orderItem : orderItems) {
                    //2)判断当前新商品 在上面的商品集合中是否已经存在
                    int indexOf = orderItemList.indexOf(orderItem);
                    if (indexOf != -1) {
                        //---存在
                        //找出对应的相同商品 追加金额
                        OrderItem oldOrderItem = orderItemList.get(indexOf);
                        oldOrderItem.setNum(oldOrderItem.getNum() + orderItem.getNum());
                        oldOrderItem.setTotalFee(BigDecimal.valueOf(oldOrderItem.getTotalFee().doubleValue() + orderItem.getTotalFee().doubleValue()));
                    } else {
                        //---不存在
                        //直接添加新商品即可
                        orderItemList.add(orderItem);
                    }
                }
            }
            Page<OrderItem> p = (Page<OrderItem>) orderItemList;
            return new PageResult(p.getTotal(), p.getResult());
        } else {
            return null;
        }

    }*/
}
