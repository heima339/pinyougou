package cn.itcast.core.controller;

import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.seckillOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seckillOrder")
public class SeckillOrderController {
    @Reference
    private seckillOrderService seckillOrderService;

    @RequestMapping("/findAll")
    public List<SeckillOrder> findAll() {
        List<SeckillOrder> seckillOrders = seckillOrderService.findAll();
        return seckillOrders;
    }

    @RequestMapping("/search")
    public PageResult search(
            // Integer pageNo, Integer pageSize,@RequestBody(required = false) Brand brand){//空指针异常
            Integer pageNo, Integer pageSize, @RequestBody SeckillOrder seckillOrder){//空指针异常
        //总条数  结果集


        PageResult pageResult = seckillOrderService.search(pageNo, pageSize, seckillOrder);
        return pageResult;


    }

    @RequestMapping("/findOne")
    public SeckillOrder findOne(Long id) {
        SeckillOrder seckillOrder = seckillOrderService.findOne(id);
        return seckillOrder;
    }
}
