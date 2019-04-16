package cn.itcast.core.controller;



import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.CountOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.OrderCount;
import entity.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/countOrder")
public class CountOrderController {

    @Reference(group = "sellerGoods")
    private CountOrderService countOrderService;
    @RequestMapping("/search")
    public PageResult search(
            // Integer pageNo, Integer pageSize,@RequestBody(required = false) Brand brand){//空指针异常
            Integer pageNo, Integer pageSize, @RequestBody Map<String,String> map) {//空指针异常
        //总条数  结果集

        return countOrderService.search(pageNo, pageSize, map);

    }
}
