package cn.itcast.core.service;

import cn.itcast.core.pojo.order.Order;

import java.util.List;

public interface OrderService {
    void add(Order order);

    List<Order> findAll();
}
