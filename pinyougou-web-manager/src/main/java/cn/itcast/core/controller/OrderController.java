package cn.itcast.core.controller;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.OrderResult;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference(group = "sellerGoods")
    private OrderService orderService;



        @RequestMapping("/search")
    public PageResult search(Integer pageNo, Integer pageSize,  @RequestBody Order order) {
            PageResult pageResult=orderService.search(pageNo,pageSize,order);

            return pageResult;
    }


}
