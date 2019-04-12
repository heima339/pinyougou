package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import entity.PageResult;

public interface SeckillGoodsService {
    PageResult search(Integer page, Integer rows, SeckillGoods goods);

    SeckillGoods findOne(Long id);

    void updateStatus(Long id, String status);

    void delete(Long id);
}
