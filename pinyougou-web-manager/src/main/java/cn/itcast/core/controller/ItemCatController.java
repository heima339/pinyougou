package cn.itcast.core.controller;

import cn.itcast.common.utils.ImportExcelUtil;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
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


    @RequestMapping("/upload")
    public Result upload(@RequestParam("myfile") MultipartFile myFile, HttpServletResponse respon, RedirectAttributes redirectAttributes){

        try {
            //先删除数据库原有的数据
           // itemCatService.deleteAll();
            ImportExcelUtil util = new ImportExcelUtil();
            InputStream input = null;
            List<List<Object>> lists = null;
            if (myFile.isEmpty()) {
                return new Result( false,"导入文件为空，请先添加Excel文件！");
            } else {
                // 如果错误为0
                String fileName = myFile.getOriginalFilename();
                input = myFile.getInputStream();
                lists = util.getBankListByExcel(input, fileName);
                input.close();
                // 循环将excel中的数据存入库
                for (int i = 1; i < lists.size(); i++) {
                    List<Object> list = lists.get(i);

                    ItemCat itemCat = new ItemCat();
                    itemCat.setId(Long.parseLong(util.getFormat(String.valueOf(list.get(0)))));
                    itemCat.setParentId(Long.parseLong(util.getFormat(String.valueOf(list.get(1)))));
                    itemCat.setName(util.getFormat(String.valueOf(list.get(2))));
                    itemCat.setTypeId(Long.parseLong(util.getFormat(String.valueOf(list.get(3)))));
                    itemCat.setItemCatStatus(util.getFormat(String.valueOf(list.get(4))));


                    itemCatService.save(itemCat);

                }

                return new Result(true,"导入成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false ,"导入文件异常请检查Excel文件！");
        }



    }
}
