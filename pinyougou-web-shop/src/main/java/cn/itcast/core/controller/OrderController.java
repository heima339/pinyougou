package cn.itcast.core.controller;

/*
* 商家后台订单管理*/

import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;

import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.OrderVo;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService  orderService;
  @RequestMapping("/findAll")
  public List<Map> findAll(){

      String name = SecurityContextHolder.getContext().getAuthentication().getName();
      //System.out.println(name);
      return orderService.findAll(name);

  }

  //根据条件查询 分页对象  订单查询
    @RequestMapping("/search")
    public PageResult search(Integer pageNo, Integer pageSize , @RequestBody  Order order){
        //商家ID
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        order.setSellerId(name);

        return  orderService.search(pageNo,pageSize,order);
    }



    ///订单发货
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids){
        try {
            orderService.updateStatus(ids);
            return new Result(true,"发货成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"发货失败");
        }
    }

}
