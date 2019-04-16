package cn.itcast.core.service;

import cn.itcast.core.pojo.order.OrderItem;
import entity.PageResult;
import vo.OrderCountVo;

public interface OrderCountService {
    PageResult search(Integer page, Integer rows, OrderItem orderItem);
}
