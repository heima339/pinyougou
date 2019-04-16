package cn.itcast.core.service;

import cn.itcast.common.utils.DateUtils;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.OrderResult;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(group = "sellerGoods")
public class OrderServiceImpl implements  OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Override
    public void add(Order order) {

    }

    @Override
    public List<Map> findAll(String name) {
        return null;
    }

    @Override
    public PageResult search(Integer pageNo, Integer pageSize, Order order) {
        //Mybatis分页插件
        PageHelper.startPage(pageNo,pageSize);
        //条件对象

//         <result column="title" property="title" jdbcType="VARCHAR" />
//    <result column="price" property="price" jdbcType="DECIMAL" />
//    <result column="num" property="num" jdbcType="INTEGER" />
        OrderQuery orderQuery = new OrderQuery();
        OrderQuery.Criteria criteria = orderQuery.createCriteria();
        if (null != order.getStatus() && !"".equals(order.getStatus())) {
            criteria.andStatusEqualTo(order.getStatus());
        }
        if (null != order.getTimeQuantum() && !"".equals(order.getTimeQuantum()) && order.getTimeQuantum() == 1) {
            String[] dayStartAndEndTimePointStr = DateUtils.getDayStartAndEndTimePointStr(new Date());
            criteria.andCreateTimeBetween(DateUtils.formatStrToDate(dayStartAndEndTimePointStr[0]),DateUtils.formatStrToDate(dayStartAndEndTimePointStr[1]));
        }
        if (null != order.getTimeQuantum() && !"".equals(order.getTimeQuantum()) && order.getTimeQuantum() == 2) {
            Date[] weekStartAndEndDate = DateUtils.getWeekStartAndEndDate(new Date());
            String[] startTimePointStr = DateUtils.getDayStartAndEndTimePointStr(weekStartAndEndDate[0]);
            String[] endTimePointStr = DateUtils.getDayStartAndEndTimePointStr(weekStartAndEndDate[1]);
            criteria.andCreateTimeBetween(DateUtils.formatStrToDate(startTimePointStr[0]), DateUtils.formatStrToDate(endTimePointStr[1]));
        }
        if (null != order.getTimeQuantum() && !"".equals(order.getTimeQuantum()) && order.getTimeQuantum() == 3) {
            Date[] monthStartAndEndDate = DateUtils.getMonthStartAndEndDate(new Date());
            String[] startTimePointStr = DateUtils.getDayStartAndEndTimePointStr(monthStartAndEndDate[0]);
            String[] endTimePointStr = DateUtils.getDayStartAndEndTimePointStr(monthStartAndEndDate[1]);
            criteria.andCreateTimeBetween(DateUtils.formatStrToDate(startTimePointStr[0]), DateUtils.formatStrToDate(endTimePointStr[1]));
        }
        //分页对象
        Page<Order> page = (Page<Order>) orderDao.selectSome(orderQuery);
        List<Order> result = page.getResult();
        for (Order o : result) {
            o.setOrderIdStr(String.valueOf(o.getOrderId()));
            OrderItemQuery orderItemQuery = new OrderItemQuery();
            orderItemQuery.createCriteria().andOrderIdEqualTo(o.getOrderId());
            List<OrderItem> orderItems = orderItemDao.selectSome(orderItemQuery);
            o.setOrderItemList(orderItems);
        }
//        Page<OrderResult> page = (Page<OrderResult>) orderDao.searchOrder(orderResult);
//        for (OrderResult o : page.getResult()) {
//            o.setOrderIdStr(String.valueOf(o.getOrderId()));
//        }

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Order> findAll() {
        return orderDao.selectByExample(null);
    }

    @Override
    public void updateStatus(Long[] ids) {

    }
}
