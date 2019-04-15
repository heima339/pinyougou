package cn.itcast.core.controller;

import cn.itcast.core.service.SalesService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sales")
public class SalesController {
    @Reference
    private SalesService salesService;

    /**
     * 商家查询指定时间段日销售数据
     */
    @RequestMapping("/getPeriodSales")
    public Map getPeriodSales(String start, String end) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return salesService.getPeriodSales(start, end, name);
    }

    /**
     * 根据指定日期查询商家销售额
     *
     * @param date
     * @return
     */
    @RequestMapping("/getSellerSales")
    public Map getSellerSales(String date) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return salesService.getSellerSales(date,name);
    }
}
