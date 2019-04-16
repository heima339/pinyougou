package cn.itcast.core.controller;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.SeckillGoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀管理
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/seckillGoods")
public class SeckillGoodsController {

    @Reference
    private SeckillGoodsService seckillGoodsService;

    //秒杀商品查询
    @RequestMapping("/search")
    public PageResult search(Integer page,Integer rows,@RequestBody SeckillGoods seckillGoods){
        //商家ID
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        seckillGoods.setSellerId(name);
        return  seckillGoodsService.search(page,rows,seckillGoods);
    }

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
