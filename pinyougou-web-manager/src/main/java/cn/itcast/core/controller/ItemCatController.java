package cn.itcast.core.controller;

import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品分类
 */
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


    //根据父ID 查询商品分类结果集  并且查询出没有被审核的
    @RequestMapping("/findByParentId1")
    public List<ItemCat> findByParentId1(Long parentId){
        return itemCatService.findByParentId1(parentId);
    }

    //查询所有
    @RequestMapping("/findAll")
    public List<ItemCat> findAll(){
        return itemCatService.findAll();
    }


    //有条件的根据分类状态和分类名称查询
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody ItemCat itemCat){
        return itemCatService.search(page,rows,itemCat);
    }

    //
    @RequestMapping("/search1")
    public PageResult search1(Integer page, Integer rows, @RequestBody ItemCat itemCat){
        return itemCatService.search1(page,rows,itemCat);
    }

    //添加
    @RequestMapping("/add")
    public Result add(@RequestBody ItemCat itemCat){
        try {
            itemCatService.add(itemCat);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }

    //删除
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try {
            itemCatService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

    //查询一个实体
    @RequestMapping("/findOne")
    public ItemCat findOne(Long id){
        return itemCatService.findOne(id);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody ItemCat itemCat){
        try {
            itemCatService.update(itemCat);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }

    }

    //修改审核状态
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids,String status){
        try {
            itemCatService.updateStatus(ids,status);
            return new Result(true,"修改状态成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改状态失败");
        }
    }
}
