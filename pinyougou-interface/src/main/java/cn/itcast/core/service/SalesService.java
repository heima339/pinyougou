package cn.itcast.core.service;

import java.util.List;
import java.util.Map;

public interface SalesService {
    /**
     * 运营商查询近7天销售数据
     * @return
     */
    Map getSevenSales();

    /**
     * 运营商查询指定日期商家销售数据
     * @param date
     * @return
     */
    List<Map<String,Object>> getSellerSales(String date);

    /**
     * 商家查询指定日期的销售数据
     * @param date
     * @param name
     * @return
     */
    Map getSellerSales(String date, String name);

    /**
     * 商家查询指定时间段的销售折线图
     * @param start
     * @param end
     * @param name
     * @return
     */
    Map getPeriodSales(String start, String end, String name);
}
