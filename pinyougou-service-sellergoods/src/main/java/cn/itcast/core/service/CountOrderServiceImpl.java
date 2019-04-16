package cn.itcast.core.service;


import cn.itcast.common.utils.DateUtils;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.OrderCount;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(group = "sellerGoods")
public class CountOrderServiceImpl implements CountOrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public PageResult search(Integer pageNo, Integer pageSize, Map<String,String> map) {
        //Mybatis分页插件
        PageHelper.startPage(pageNo,pageSize);
        //String[] timePoint = new String[2];
        if (null != map.get("timeQuantum") && !"".equals(map.get("timeQuantum")) &&"1".equals(map.get("timeQuantum"))) {
            String[] startAndEndTimePointStr = DateUtils.getDayStartAndEndTimePointStr(new Date());
            map.put("startTime", startAndEndTimePointStr[0]);
            map.put("endTime", startAndEndTimePointStr[1]);
        }
        if (null != map.get("timeQuantum") && !"".equals(map.get("timeQuantum")) &&"2".equals(map.get("timeQuantum"))) {
            Date[] weekStartAndEndDate = DateUtils.getWeekStartAndEndDate(new Date());
            map.put("startTime",DateUtils.getDayStartAndEndTimePointStr(weekStartAndEndDate[0])[0]);
            map.put("endTime", DateUtils.getDayStartAndEndTimePointStr(weekStartAndEndDate[1])[1]);
        }
        if (null != map.get("timeQuantum") && !"".equals(map.get("timeQuantum")) &&"2".equals(map.get("timeQuantum"))) {
            Date[] monthStartAndEndDate = DateUtils.getMonthStartAndEndDate(new Date());
            map.put("startTime",DateUtils.getDayStartAndEndTimePointStr(monthStartAndEndDate[0])[0]);
            map.put("endTime", DateUtils.getDayStartAndEndTimePointStr(monthStartAndEndDate[1])[1]);

        }
        Page<OrderCount> page = (Page<OrderCount>) orderDao.countOrder(map);


        //PageInfo<Brand> info = new PageInfo<>(brandList);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
