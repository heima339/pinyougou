package cn.itcast.core.controller;

import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 商品分类
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    //根据父ID 查询商品分类结果集
    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId){
        return itemCatService.findByParentId(parentId);
    }
    //查询一个商品分类
    @RequestMapping("/findOne")
    public ItemCat findOne(Long id){
        return itemCatService.findOne(id);
    }
    //查询所有
    @RequestMapping("/findAll")
    public List<ItemCat> findAll(){
        return itemCatService.findAll();
    }
    //商品申请
    @RequestMapping("/add")
    public Result add(@RequestBody ItemCat itemCat){
        try {
            itemCatService.add(itemCat);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }

    //查询所有分类 返回值 List<Map>
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return itemCatService.selectOptionList();
    }

}
