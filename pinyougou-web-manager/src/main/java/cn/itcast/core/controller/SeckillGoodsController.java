package cn.itcast.core.controller;

import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.SeckillGoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
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

    //根据条件查询 分页对象
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody SeckillGoods goods) {

        return seckillGoodsService.search(page, rows, goods);
    }


    //查询一个包装对象SeckillGoods
    @RequestMapping("/findOne")
    public SeckillGoods findOne(Long id) {
        return seckillGoodsService.findOne(id);
    }


    ///开始审核
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            if (ids != null && ids.length > 0) {
                for (Long id : ids) {

                    seckillGoodsService.updateStatus(id, status);
                }
            }

            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    //删除
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            if (ids != null && ids.length > 0) {
                for (Long id : ids) {
                    seckillGoodsService.delete(id);
                }
            }
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }
}
