package cn.itcast.core.controller;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.SeckillGoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀商品管理
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/seckillGoods")
public class SeckillGoodsController {

    @Reference
    private SeckillGoodsService seckillGoodsService;

    //秒杀商品申请 新增
    @RequestMapping("/add")
    public Result add(@RequestBody SeckillGoods seckillGoods){
        try {
            seckillGoodsService.add(seckillGoods);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }
}
