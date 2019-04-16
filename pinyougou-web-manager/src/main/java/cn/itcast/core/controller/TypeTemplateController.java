package cn.itcast.core.controller;

import cn.itcast.common.utils.ImportExcelUtil;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.service.TypeTemplateService;
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
 * 模板管理
 */

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {


    @Reference
    private TypeTemplateService typeTemplateService;
    //查询分页对象 有条件
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody TypeTemplate tt){

        return typeTemplateService.search(page,rows,tt);

    }
    //添加
    @RequestMapping("/add")
    public Result add(@RequestBody TypeTemplate tt){
        try {
            typeTemplateService.add(tt);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }
    //添加
    @RequestMapping("/update")
    public Result update(@RequestBody TypeTemplate tt){
        try {
            typeTemplateService.update(tt);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }
    //查询一个模板
    @RequestMapping("/findOne")
    public TypeTemplate findOne(Long id){
        return typeTemplateService.findOne(id);
    }



    @RequestMapping("/upload")
    public Result upload(@RequestParam("myfile") MultipartFile myFile, HttpServletResponse respon, RedirectAttributes redirectAttributes){

        try {
            //先删除数据库原有的数据
           // typeTemplateService.deleteAll();
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

                    TypeTemplate typeTemplate = new TypeTemplate();
                    typeTemplate.setId(Long.parseLong(String.valueOf(list.get(0))));
                    typeTemplate.setName(String.valueOf(list.get(1)));
                    typeTemplate.setSpecIds(String.valueOf(list.get(2)));
                    typeTemplate.setBrandIds(String.valueOf(list.get(3)));
                    typeTemplate.setCustomAttributeItems(String.valueOf(list.get(4)));
                    typeTemplate.setStatus(String.valueOf(list.get(5)));


                    typeTemplateService.save(typeTemplate);

                }

                return new Result(true,"导入成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false ,"导入文件异常请检查Excel文件！");
        }



    }

}
