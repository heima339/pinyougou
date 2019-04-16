package cn.itcast.core.service;

import cn.itcast.common.utils.DateUtils;
import cn.itcast.common.utils.IdWorker;
import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.log.PayLogDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.log.PayLog;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;
import vo.Cart;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单管理
 */
@Service
@Transactional
public class OrderServiceImpl implements  OrderService {

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
    private  GoodsDao goodsDao;

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
        payLog.setTotalFee(total*100);

        //用户id
        payLog.setUserId(order.getUserId());


        //支付状态
        payLog.setTradeState("0");


        //订单集合 [123,456,6787]
        payLog.setOrderList(ids.toString().replace("[","").replace("]",""));

        //付款方式
        payLog.setPayType("1");

        //保存完成
        payLogDao.insertSelective(payLog);

        //保存缓存一份
        redisTemplate.boundHashOps("payLog").put(order.getUserId(),payLog);

        //清空
        //redisTemplate.boundHashOps("CART").delete(order.getUserId());

    }

    @Override
    public List<Map> findAll( String name) {
        List<Map> list = new ArrayList<>();

        OrderQuery orderQuery = new OrderQuery();
        OrderQuery.Criteria criteria1 = orderQuery.createCriteria();
              criteria1.andSellerIdEqualTo(name);
        List<Order> orders = orderDao.selectByExample(orderQuery);


        for (Order order : orders) {


            OrderItemQuery query = new OrderItemQuery();
            OrderItemQuery.Criteria criteria = query.createCriteria();
            criteria.andOrderIdEqualTo(order.getOrderId());

            List<OrderItem> orderItems = orderItemDao.selectByExample(query);
            for (OrderItem orderItem : orderItems) {

                HashMap<String, String> map = new HashMap<>();

                //订单来源
                map.put("sourceType",order.getSourceType());
                //创建时间
                map.put("createTime",String.valueOf(order.getCreateTime()));
                //状态
                map.put("status",order.getStatus());


                   //商品价格
                map.put("price",String.valueOf(orderItem.getPrice()));
                  //数量
                   map.put("num",String.valueOf(orderItem.getNum()));
                   //小计
                   map.put("totalFee",String.valueOf(orderItem.getTotalFee()));
                   //商品价格

                Goods goods = goodsDao.selectByPrimaryKey(orderItem.getGoodsId());
                  map.put("goodsName",goods.getGoodsName());
                list.add(map);
            }

        }

       return list;
    }

    @Override
    public PageResult search(Integer page, Integer rows, Order order )  {
        //分页插件
        PageHelper.startPage(page, rows);



        //条件 对象
        OrderQuery orderQuery = new OrderQuery();
        OrderQuery.Criteria criteria = orderQuery.createCriteria();



        if(null!=order.getStatus()&&!"".equals(order.getStatus())){
              criteria.andStatusEqualTo( order.getStatus());
          }

          if (null!=order.getSellerId()){
              criteria.andSellerIdEqualTo(order.getSellerId());
          }




        try {
            if ("0".equals(order.getOrderDate())) {

                //日销售额
                String[] dayDate = DateUtils.getDayStartAndEndTimePointStr(new Date());

                Date dayStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dayDate[0]);
                Date dayEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dayDate[1]);

                criteria.andCreateTimeBetween(dayStart,dayEnd);


            }
            if ("1".equals(order.getOrderDate())) {

                //周销售额
                Date[] weekDate = DateUtils.getWeekStartAndEndDate(new Date());

                    criteria.andCreateTimeBetween(weekDate[0],weekDate[1]);
            }
            if ("2".equals(order.getOrderDate())) {

                //月销售额
                Date[] monthDate = DateUtils.getMonthStartAndEndDate(new Date());
                criteria.andCreateTimeBetween(monthDate[0],monthDate[1]);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }





        Page<Order> orders = (Page<Order>) orderDao.selectByExample(orderQuery);
        for (Order o : orders) {
            OrderItemQuery orderItemQuery = new OrderItemQuery();
            orderItemQuery.createCriteria().andOrderIdEqualTo(o.getOrderId());
            List<OrderItem> orderItems = orderItemDao.selectByExample(orderItemQuery);
            //设置到oeder对象中
            o.setOrderItemList(orderItems);
            for (OrderItem orderItem : orderItems) {
//                GoodsQuery goodsQuery = new GoodsQuery();
//                goodsQuery.createCriteria().andIdEqualTo(orderItem.getGoodsId());
                Goods goods = goodsDao.selectByPrimaryKey(orderItem.getGoodsId());
                orderItem.setGoodsName(goods.getGoodsName());
            }

        }

        //Page<OrderItem> p = (Page<OrderItem>) orderItemDao.selectByExample(null);


        return new PageResult(orders.getTotal(), orders.getResult());

    }



        //开始发货

        @Override
        public void updateStatus(Long[] ids) {
            Order order =new Order();

            //状态
            order.setStatus("4");

            for (Long id : ids) {


                order.setOrderId(id);
                 //更细订单的状态
               orderDao.updateByPrimaryKeySelective(order);


                }

            }
}
