package cn.itcast.core.controller;

import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 商品分类展示
 */
@RestController
@RequestMapping("itemCat")

public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    //"itemCat/findItemCat.do?"
    @RequestMapping("findItemCat")
    public Map<String, Map> findItemCat() {
        Map<String, Map> itemCatMap = itemCatService.findItemCat();
        return itemCatMap;
    }


}
