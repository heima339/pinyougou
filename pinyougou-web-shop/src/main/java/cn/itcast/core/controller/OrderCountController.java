package cn.itcast.core.controller;

import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.OrderCountService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.OrderCountVo;

/**
 * 订单统计管理
 */
@RestController
@RequestMapping("/orderCount")
public class OrderCountController {

    @Reference
    private OrderCountService orderCountService;

    //查询
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows , @RequestBody OrderItem orderItem){
        //商家ID
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        orderItem.setSellerId(name);
        return  orderCountService.search(page,rows,orderItem);
    }
}
