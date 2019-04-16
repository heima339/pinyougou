package cn.itcast.core.service;

import cn.itcast.common.utils.DateUtils;
import cn.itcast.common.utils.IdWorker;
import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.log.PayLogDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.log.PayLog;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import vo.Cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单管理
 */
@SuppressWarnings("all")
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PayLogDao payLogDao;
    @Autowired
    private GoodsDao goodsDao;


    @Override
    public void add(Order order) {

        //缓存中购物车
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("CART").get(order.getUserId());


        //日志表  支付金额
        long total = 0;
        //订单集合
        List<Long> ids = new ArrayList<>();


        for (Cart cart : cartList) {
            //保存订单

            //订单ID  分布式ID
            long id = idWorker.nextId();
            ids.add(id);
            order.setOrderId(id);
            //金额
            double totalPrice = 0;

            List<OrderItem> orderItemList = cart.getOrderItemList();
            for (OrderItem orderItem : orderItemList) {
                //库存ID
                Item item = itemDao.selectByPrimaryKey(orderItem.getItemId());

                //数量
                //订单详情ID
                orderItem.setId(idWorker.nextId());
                //商品id
                orderItem.setGoodsId(item.getGoodsId());
                //订单ID 外键
                orderItem.setOrderId(id);
                //标题
                orderItem.setTitle(item.getTitle());
                //价格
                orderItem.setPrice(item.getPrice());
                //小计
                orderItem.setTotalFee(item.getPrice().multiply(new BigDecimal(orderItem.getNum())));

                totalPrice += orderItem.getTotalFee().doubleValue();

                //图片路径
                orderItem.setPicPath(item.getImage());
                //商家ID
                orderItem.setSellerId(item.getSellerId());

                //保存订单详情表
                orderItemDao.insertSelective(orderItem);

            }
            //给订单表设置金额
            order.setPayment(new BigDecimal(totalPrice));

            total += order.getPayment().longValue();

            //订单状态
            order.setStatus("1");
            //时间
            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());

            //订单来源
            order.setSourceType("2");
            //商家ID
            order.setSellerId(cart.getSellerId());
            //保存订单
            orderDao.insertSelective(order);

        }

        //保存日志表
        PayLog payLog = new PayLog();
        //ID 支付订单ID
        payLog.setOutTradeNo(String.valueOf(idWorker.nextId()));

        //时间
        payLog.setCreateTime(new Date());
        //付款时间
        //总金额 分
        payLog.setTotalFee(total * 100);

        //用户id
        payLog.setUserId(order.getUserId());


        //支付状态
        payLog.setTradeState("0");


        //订单集合 [123,456,6787]
        payLog.setOrderList(ids.toString().replace("[", "").replace("]", ""));

        //付款方式
        payLog.setPayType("1");

        //保存完成
        payLogDao.insertSelective(payLog);

        //保存缓存一份
        redisTemplate.boundHashOps("payLog").put(order.getUserId(), payLog);

        //清空
        //redisTemplate.boundHashOps("CART").delete(order.getUserId());

    }

    //统计销售额
    @Override
    public List<OrderItem> orderCount(String status,OrderItem firstOrderItem) {
        //获取当前商家名称

        if ("1".equals(status)) {
            List<OrderItem> orderItemList = new ArrayList<>();
            orderItemList.add(firstOrderItem);
            //日销售额
            String[] dayDate = DateUtils.getDayStartAndEndTimePointStr(new Date());
            OrderQuery orderQuery = new OrderQuery();
            //获取当前商家当日成交订单
            orderQuery.createCriteria().andUpdateTimeBetween(new Date(dayDate[0]), new Date(dayDate[1])).andSellerIdEqualTo(firstOrderItem.getSellerId());
            List<Order> orders = orderDao.selectByExample(orderQuery);
            if (null != orders && orders.size() > 0) {
                //遍历订单集合
                for (Order order : orders) {
                    //获取订单id
                    Long orderId = order.getOrderId();
                    //获取订单项
                    List<OrderItem> orderItems = (List<OrderItem>) orderItemDao.selectByPrimaryKey(orderId);

                    for (OrderItem orderItem : orderItems) {
                        //2)判断当前新商品 在上面的商品集合中是否已经存在
                        int indexOf = orderItemList.indexOf(orderItem);
                        if (indexOf != -1) {
                            //---存在
                            //找出对应的相同商品 追加金额
                            OrderItem oldOrderItem = orderItemList.get(indexOf);
                            oldOrderItem.setTotalFee(BigDecimal.valueOf(oldOrderItem.getTotalFee().doubleValue() + orderItem.getTotalFee().doubleValue()));
                        } else {
                            //---不存在
                            //直接添加新商品即可
                            orderItemList.add(orderItem);
                        }
                    }
                }
                return orderItemList;
            } else {
                return null;
            }

        } else if ("2".equals(status)) {
            List<OrderItem> orderItemList = new ArrayList<>();
            orderItemList.add(firstOrderItem);
            //周销售额
            Date[] weekDate = DateUtils.getWeekStartAndEndDate(new Date());
            OrderQuery orderQuery = new OrderQuery();
            //获取当前商家当周成交订单
            orderQuery.createCriteria().andUpdateTimeBetween(weekDate[0], weekDate[1]).andSellerIdEqualTo(firstOrderItem.getSellerId());
            List<Order> orders = orderDao.selectByExample(orderQuery);
            if (null != orders && orders.size() > 0) {
                //遍历订单集合
                for (Order order : orders) {
                    //获取订单id
                    Long orderId = order.getOrderId();
                    //获取订单项
                    List<OrderItem> orderItems = (List<OrderItem>) orderItemDao.selectByPrimaryKey(orderId);

                    for (OrderItem orderItem : orderItems) {
                        //2)判断当前新商品 在上面的商品集合中是否已经存在
                        int indexOf = orderItemList.indexOf(orderItem);
                        if (indexOf != -1) {
                            //---存在
                            //找出对应的相同商品 追加金额
                            OrderItem oldOrderItem = orderItemList.get(indexOf);
                            oldOrderItem.setTotalFee(BigDecimal.valueOf(oldOrderItem.getTotalFee().doubleValue() + orderItem.getTotalFee().doubleValue()));
                        } else {
                            //---不存在
                            //直接添加新商品即可
                            orderItemList.add(orderItem);
                        }
                    }
                }
                return orderItemList;
            } else {
                return null;
            }

        } else {
            List<OrderItem> orderItemList = new ArrayList<>();
            orderItemList.add(firstOrderItem);
            //月销售额
            Date[] monthDate = DateUtils.getMonthStartAndEndDate(new Date());
            OrderQuery orderQuery = new OrderQuery();
            //获取当前商家当月成交订单
            orderQuery.createCriteria().andUpdateTimeBetween(monthDate[0], monthDate[1]).andSellerIdEqualTo(firstOrderItem.getSellerId());
            List<Order> orders = orderDao.selectByExample(orderQuery);
            if (null != orders && orders.size() > 0) {
                //遍历订单集合
                for (Order order : orders) {
                    //获取订单id
                    Long orderId = order.getOrderId();
                    //获取订单项
                    List<OrderItem> orderItems = (List<OrderItem>) orderItemDao.selectByPrimaryKey(orderId);

                    for (OrderItem orderItem : orderItems) {
                        //2)判断当前新商品 在上面的商品集合中是否已经存在
                        int indexOf = orderItemList.indexOf(orderItem);
                        if (indexOf != -1) {
                            //---存在
                            //找出对应的相同商品 追加金额
                            OrderItem oldOrderItem = orderItemList.get(indexOf);
                            oldOrderItem.setTotalFee(BigDecimal.valueOf(oldOrderItem.getTotalFee().doubleValue() + orderItem.getTotalFee().doubleValue()));
                        } else {
                            //---不存在
                            //直接添加新商品即可
                            orderItemList.add(orderItem);
                        }
                    }
                }
                return orderItemList;
            } else {
                return null;
            }

        }
    }
}
