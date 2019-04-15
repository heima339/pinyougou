package cn.itcast.core.controller;

import cn.itcast.core.service.SalesService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/sales")
public class SalesController {
    @Reference
    private SalesService salesService;
    /**
     * 查询7日销售数据
     * key string     value 数组
     日期			sevenDays[1,2,3,4,5,6,7]
     数据			sevenDataSales[100,343,4323,4352,234,67,564]
     */
    @RequestMapping("/getSevenSales")
    public Map getSevenSales(){
        return salesService.getSevenSales();
    }
}
