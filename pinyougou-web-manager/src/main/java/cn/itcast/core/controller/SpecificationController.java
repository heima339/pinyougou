package cn.itcast.core.controller;

import cn.itcast.common.utils.ImportExcelUtil;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.service.SpecificationService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vo.SpecificationVo;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 规格管理
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/specification")
public class SpecificationController {


    @Reference
    private SpecificationService specificationService;

    //规格分页查询
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody Specification specification){
        return specificationService.search(page,rows,specification);
    }

    //规格分页查询  查询未审核的
    @RequestMapping("/search1")
    public PageResult search1(Integer page, Integer rows, @RequestBody Specification specification){
        return specificationService.search1(page,rows,specification);
    }

    //规格添加
    @RequestMapping("/add")
    public Result add(@RequestBody SpecificationVo specificationVo){
        try {

            specificationService.add(specificationVo);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }
    //规格修改
    @RequestMapping("/update")
    public Result update(@RequestBody SpecificationVo specificationVo){
        try {
            specificationService.update(specificationVo);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }
    //查询一个规格包装对象  规格  规格选项
    @RequestMapping("/findOne")
    public SpecificationVo findOne(Long id){
        return specificationService.findOne(id);
    }

    //查询所有规格  返回值 List<Map
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return specificationService.selectOptionList();
    }

    //修改状态
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids,String status){

        try {
            specificationService.updateStatus(ids,status);
            return new Result(true,"修改状态成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改状态失败");
        }


    }


    //删除规格
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try {
            specificationService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

    @RequestMapping("/upload")
    public Result upload(@RequestParam("myfile") MultipartFile myFile, HttpServletResponse respon, RedirectAttributes redirectAttributes){

        try {
            //先删除数据库原有的数据
           //specificationService.deleteAll();
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

                    Specification specification = new Specification();
                    specification.setId(Long.parseLong(util.getFormat(String.valueOf(list.get(0)))));
                    specification.setSpecName(util.getFormat(String.valueOf(list.get(1))));
                    specification.setSpecStatus(util.getFormat(String.valueOf(list.get(2))));


                    specificationService.save(specification);

                }

                return new Result(true,"导入成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false ,"导入文件异常请检查Excel文件！");
        }



    }
}
