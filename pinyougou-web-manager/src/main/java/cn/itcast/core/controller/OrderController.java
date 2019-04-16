package cn.itcast.core.controller;

import cn.itcast.common.utils.ExportExcel;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.OrderResult;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference(group = "sellerGoods")
    private OrderService orderService;
    @RequestMapping("/outputer")
    public Result outputer(HttpServletResponse response){

        ExportExcel<Order> ex = new ExportExcel<>();
        //设置列名
        String[] headers = {"订单id","实付金额","支付类型","邮费","状态","订单创建时间","订单更新时间","付款时间",
                "发货时间","交易完成时间","交易关闭时间","物流名称","物流单号","用户id","买家留言",
                "买家昵称","买家是否已经评价","收货人地区","收货人手机","收货人邮编",
                "收货人","过期时间","发票类型","订单来源","商家ID"};

        OutputStream out =null;
        //查出所有集合
        List<Order> orderList = orderService.findAll();


        try {

            String returnName =  new String("订单汇总表.xls".getBytes(), "ISO-8859-1");
            response.setContentType("application/octet-stream");
            response.setContentType("application/OCTET-STREAM;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + returnName);
            out= response.getOutputStream();
            ex.exportExcel(headers, orderList,out);
            if(out!=null){
                try {
                    out.close();

                } catch (IOException e) {
                    e.printStackTrace();

                }

            }
            return new Result(true,"导出成功");

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"导出失败");
        }

    }

    @RequestMapping("/search")
    public PageResult search(Integer pageNo, Integer pageSize,  @RequestBody Order order) {
        PageResult pageResult=orderService.search1(pageNo,pageSize,order);

        return pageResult;
    }


}
