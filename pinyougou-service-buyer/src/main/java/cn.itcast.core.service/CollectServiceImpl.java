package cn.itcast.core.service;

import cn.itcast.core.pojo.order.OrderItem;
import com.alibaba.dubbo.config.annotation.Service;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import vo.Cart;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏管理
 */
@Service
@Transactional
public class CollectServiceImpl implements CollectService {
    @Autowired
    private RedisTemplate redisTemplate;

    //将库存id存入缓存当中
    @Override
    public void addItemIdToRedis(Long itemId, String name) {


        Object collect = redisTemplate.boundHashOps("collect").get(name);

        if (null == collect) {    //如果没有就新建一个
            collect = new ArrayList<>();
        }

        ArrayList<Long> list = (ArrayList<Long>) collect;


        if (!list.contains(itemId)) {
            list.add(itemId);
            redisTemplate.boundHashOps("collect").put(name, list);
        }

        //上面的看情况执行 下面的一定执行！！！


            //--封装一个方法？？    deleteOrderItem 删除购物项
            List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("CART").get(name);
            for (Cart cart : cartList) {
                List<OrderItem> orderItemList = cart.getOrderItemList();
                for (OrderItem orderItem : orderItemList) {
                    if(itemId.equals(orderItem.getItemId())){   //Long 是一个类 如果只是为了比较数值 不能用==进行比较
                        orderItemList.remove(orderItem);
                        break;
                    }
                }
            }
            redisTemplate.boundHashOps("CART").put(name, cartList);
        System.out.println(list);

        //清空缓存  //需要时可以打开
        //redisTemplate.boundHashOps("collect").delete(name);

    }

    @Test
    public void test01(){

    }

}
