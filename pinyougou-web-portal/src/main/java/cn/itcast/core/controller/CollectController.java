package cn.itcast.core.controller;

import cn.itcast.core.service.CollectService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收藏管理
 */
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Reference
    private CollectService collectService;

    //将库存id存入缓存当中
    // addItemIdToRedis
    @RequestMapping("/addItemIdToRedis.do")
    public Result addItemIdToRedis(Long itemId){
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            collectService.addItemIdToRedis(itemId,name);
            return new Result(true,"加入缓存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"加入缓存失败");
        }
    }
}
