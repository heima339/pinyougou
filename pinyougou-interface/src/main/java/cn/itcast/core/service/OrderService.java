package cn.itcast.core.service;

import cn.itcast.core.pojo.order.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    void add(Order order);


    List<Map> findAll(String name);
}
