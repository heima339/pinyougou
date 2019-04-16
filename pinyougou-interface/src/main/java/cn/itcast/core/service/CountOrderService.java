package cn.itcast.core.service;

import cn.itcast.core.pojo.order.Order;
import entity.OrderCount;
import entity.PageResult;

import java.util.Map;

public interface CountOrderService {

    PageResult search(Integer pageNo, Integer pageSize, Map<String,String> map);
}
