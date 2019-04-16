package cn.itcast.core.controller;

import cn.itcast.common.utils.ImportExcelUtil;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 品牌管理
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/brand")
public class BrandController {


    @Reference
    private BrandService brandService;




    private final static String XLS = "xls";
    private final static String XLSX = "xlsx";


    //查询所有品牌
    @RequestMapping("/findAll")
    public List<Brand> findAll(){


        return brandService.findAll();

    }
    //查询分页对象
    @RequestMapping("/findPage")
    public PageResult findPage(Integer pageNo, Integer pageSize){
        //总条数  结果集

       return brandService.findPage(pageNo,pageSize);

    }
    //查询分页对象  有条件  页面传递的是id
    @RequestMapping("/search")
    public PageResult search(
           // Integer pageNo, Integer pageSize,@RequestBody(required = false) Brand brand){//空指针异常
            Integer pageNo, Integer pageSize, @RequestBody Brand brand){//空指针异常
        //总条数  结果集



       return brandService.search(pageNo,pageSize,brand);

    }
    //添加
    @RequestMapping("/add")
    public Result add(@RequestBody Brand brand){

        try {
            brandService.add(brand);
            return new Result(true,"保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"保存失败");
        }

    }
    //修改
    @RequestMapping("/update")
    public Result update(@RequestBody Brand brand){

        try {
            brandService.update(brand);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }

    }
    //修改
    @RequestMapping("/delete")
    public Result delete(Long[] ids){

        try {
            brandService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }

    }
    //查询一个品牌
    @RequestMapping("/findOne")
    public Brand findOne(Long id){
        return brandService.findOne(id);
    }

    //查询所有品牌 返回值 List<Map>
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return brandService.selectOptionList();
    }


    /**
     * 批量导入数据
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("myfile") MultipartFile myFile, HttpServletResponse respon, RedirectAttributes redirectAttributes) {

        try {
            //先删除数据库原有的数据
            //brandService.deleteAll();
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
                    Brand brand = new Brand();
                    brand.setId(Long.parseLong(util.getFormat(String.valueOf(list.get(0)))));
                    brand.setName(util.getFormat(String.valueOf(list.get(1))));
                    brand.setFirstChar(util.getFormat(String.valueOf(list.get(2))));
                    brand.setStatus(util.getFormat(String.valueOf(list.get(3))));

                    brandService.save(brand);

                }

                return new Result(true,"导入成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
           return  new Result(false ,"导入文件异常请检查Excel文件！");
        }


    }
}
