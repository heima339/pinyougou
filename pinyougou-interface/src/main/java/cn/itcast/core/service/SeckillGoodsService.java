package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import entity.PageResult;

public interface SeckillGoodsService {

    PageResult search(Integer page, Integer rows, SeckillGoods seckillGoods);

    void add(SeckillGoods seckillGoods);
}
