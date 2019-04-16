package cn.itcast.core.controller;

import cn.itcast.common.utils.ExportExcel;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.GoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.GoodsVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 商品管理
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/goods")
public class GoodsController {


    @Reference
    private GoodsService goodsService;
    //商品添加
    @RequestMapping("/add")
    public Result add(@RequestBody GoodsVo vo){
        try {
            //商家ID
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            vo.getGoods().setSellerId(name);
            goodsService.add(vo);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }
    //商品修改
    @RequestMapping("/update")
    public Result update(@RequestBody GoodsVo vo){
        try {

            goodsService.update(vo);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }


    //根据条件查询 分页对象
    @RequestMapping("/search")
    public PageResult search(Integer page,Integer rows ,@RequestBody Goods goods){
        //运营商后台管理商品的时候 对所有商家进行统一处理

        return  goodsService.search(page,rows,goods);
    }


    //查询一个包装对象GoodsVo
    @RequestMapping("/findOne")
    public GoodsVo findOne(Long id){
        return goodsService.findOne(id);
    }



    ///开始审核
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids,String status){
        try {
            goodsService.updateStatus(ids,status);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }
    //删除
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try {
            goodsService.delete(ids);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }

    /**
     * 从数据库中导出到excel中
     * 用户（商品、订单）数据导出
     * @return
     */

    @RequestMapping("/outputer")
    public Result outputer(HttpServletResponse response){

        ExportExcel<Goods> ex = new ExportExcel<>();
        //设置列名
        String[] headers = {"id","商家ID","SPU名","默认SKU","状态","是否上架","品牌","副标题",
                "一级类目","二级类目","三级类目","小图","商城价","分类模板ID","是否启用规格",
                "是否删除"};

        OutputStream out =null;
        //查出所有集合
        List<Goods> goodsList = goodsService.findAll();


        try {


            String returnName =  new String("商品汇总表.xls".getBytes(), "ISO-8859-1");
            response.setContentType("application/octet-stream");
            response.setContentType("application/OCTET-STREAM;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + returnName);
            out= response.getOutputStream();
            ex.exportExcel(headers, goodsList,out);
            if(out!=null){
                try {
                    out.close();

                } catch (IOException e) {
                    e.printStackTrace();

                }

            }
            return new Result(true,"导出成功");

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"导出失败");
        }



    }

}
