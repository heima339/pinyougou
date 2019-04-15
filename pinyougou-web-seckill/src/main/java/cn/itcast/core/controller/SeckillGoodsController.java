package cn.itcast.core.controller;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.SeckillGoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.zookeeper.data.Id;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/seckillGoods")
public class SeckillGoodsController {

    @Reference
    private SeckillGoodsService seckillGoodsService;

    @RequestMapping("/findList")
    public List<SeckillGoods> findList(){

       List<SeckillGoods>list=  seckillGoodsService.findList();
        return list;
    }

    @RequestMapping("/findOneFromRedis")
    public SeckillGoods findOneFromRedis(Long id){
       SeckillGoods seckillGoods=  seckillGoodsService.findOneFromRedis(id);


       return  seckillGoods;
    }
}
