package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillOrder;
import entity.PageResult;

import java.util.List;

public interface seckillOrderService {
    List<SeckillOrder> findAll();

    PageResult search(Integer pageNo, Integer pageSize, SeckillOrder seckillOrder);

    SeckillOrder findOne(Long id);
}
