package cn.itcast.core.controller;

import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.service.MyOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.OrderVo;

import java.util.List;

/**
 * 订单管理
 */
@RestController
@RequestMapping("/myOrder")
public class MyOrderController {

    @Reference
    private MyOrderService myOrderService;

    //订单查询  http://localhost:8089/myOrder/search.do


    //查询分页对象 有条件
    @RequestMapping("/search.do")
    public PageResult search(Integer page, Integer rows, String status) {
        if (null == status) {
            //status = ;
            System.out.println("查询所有");
        }else{
            System.out.println(status);
        }
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        PageResult pageResult = myOrderService.search(page, rows, name, status);


        return pageResult;
    }

    //查询库存对象集合  //根据缓存
    @RequestMapping("/loadItemList.do")
    public List<Item> loadItemList(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return myOrderService.loadItemList(name);
    }


}
