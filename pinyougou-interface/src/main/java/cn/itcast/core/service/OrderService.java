package cn.itcast.core.service;

import cn.itcast.core.pojo.order.Order;
import entity.PageResult;

import java.util.List;
import java.util.Map;

public interface OrderService {
    void add(Order order);


    List<Map> findAll(String name);

    PageResult search(Integer page, Integer rows, Order order);

    void updateStatus(Long[] ids);
}
