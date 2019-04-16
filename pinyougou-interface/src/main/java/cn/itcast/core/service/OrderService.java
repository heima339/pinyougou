package cn.itcast.core.service;

import cn.itcast.core.pojo.order.Order;
import entity.OrderResult;
import entity.PageResult;

import java.util.List;
import java.util.Map;

import java.util.List;

public interface OrderService {
    void add(Order order);


    List<Map> findAll(String name);


    PageResult search(Integer pageNo, Integer pageSize, Order order);

    List<Order> findAll();
}
