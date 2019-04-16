package cn.itcast.core.controller;


import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.SeckillOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀订单管理
 */
@RestController
@RequestMapping("/seckillOrder")
public class SeckillOrderController {

    @Reference
    private SeckillOrderService seckillOrderService;

    //查询
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows , @RequestBody SeckillOrder seckillOrder){
        //商家ID
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        seckillOrder.setSellerId(name);
        return  seckillOrderService.search(page,rows,seckillOrder);
    }
}
