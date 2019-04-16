package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.pojo.seller.SellerQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.PageResult;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import vo.OrderVo;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MyOrderServiceImpl implements MyOrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private SellerDao sellerDao;
    @Autowired
    private OrderItemDao orderItemDao;


    //查询分页对象    //有条件


    //查询分页对象
    @Override
    public PageResult search(Integer page, Integer rows, String name, String status) {
        //根据用户名查询订单表
        OrderQuery orderQuery = new OrderQuery();
        OrderQuery.Criteria criteria = orderQuery.createCriteria();
        if (null != name && !"".equals(name.trim())) {
            criteria.andUserIdEqualTo(name);
        }
        if (null != status && !"".equals(status.trim())) {
            criteria.andStatusEqualTo(status);
        }

        //开始分页？
        PageHelper.startPage(page, rows);

        List<Order> orderList = orderDao.selectByExample(orderQuery);

        PageInfo<Order> orderPageInfo = new PageInfo<>(orderList);

        long total = orderPageInfo.getTotal();

        int size = orderList.size();
        System.out.println(size);

        ArrayList<OrderVo> orderVoList = new ArrayList<>();
        OrderVo orderVo = null;
        for (Order order : orderList) {
            orderVo = new OrderVo();

            OrderItemQuery orderItemQuery = new OrderItemQuery();
            OrderItemQuery.Criteria criteria1 = orderItemQuery.createCriteria();
            criteria1.andOrderIdEqualTo(order.getOrderId());
            List<OrderItem> orderItemList = orderItemDao.selectByExample(orderItemQuery);
            orderVo.setCreate_time(order.getCreateTime());
            Long orderId = order.getOrderId();
            System.out.println(orderId);
            orderVo.setOrder_id(orderId);
            orderVo.setOrder_id_str(String.valueOf(orderId));
            System.out.println(orderVo.getOrder_id_str());
            //TODO 增加一个String类型的订单号

            String sellerId = order.getSellerId();
            if (null != sellerId && !"".equals(sellerId.trim())) {
                Seller seller = sellerDao.selectByPrimaryKey(sellerId);
                orderVo.setNick_name(seller.getNickName());  //TODO  查询 nick_name
            }
            orderVo.setOrderItemList(orderItemList); //TODO 已查
            orderVo.setPayment(order.getPayment());
            orderVo.setStatus(order.getStatus());

            orderVoList.add(orderVo);
        }

        //int 转 Long        //自己的数据库 48 23 8 0 0 条？
        return new PageResult(/*Long.parseLong(String.valueOf(size))*/total, orderVoList);
    }


    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ItemDao itemDao;

    //查询库存对象集合  //根据缓存
    @Override
    public List<Item> loadItemList(String name) {
        ArrayList<Long> list = (ArrayList<Long>) redisTemplate.boundHashOps("collect").get(name);
        List<Item> itemList = new ArrayList<>();
        for (Long itemId : list) {
            Item item = itemDao.selectByPrimaryKey(itemId);
            itemList.add(item);
        }

        return itemList;
    }


}
