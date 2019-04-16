package cn.itcast.core.controller;

import cn.itcast.common.utils.ExportExcel;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.UserService1;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService1 userService1;

    //搜索
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody User user){

      return userService1.search(page,rows,user);
    }

    //查询一个实体
    @RequestMapping("/findOne")
    public User findOne(Long id){
        return userService1.findOne(id);
    }

    //改变用户使用状态
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long id,String status){
        try {
            userService1.updateStatus(id,status);
            return new Result(true,"修改状态成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改状态失败");
        }
    }


    /**
     * 从数据库中导出到excel中
     * 用户（商品、订单）数据导出
     * @return
     */
    @RequestMapping("/outputer")
    public Result outputer(HttpServletResponse response){
        ExportExcel<User> ex = new ExportExcel<>();
        //设置列名
        String[] headers = {"id","用户名","密码","手机号","邮箱","创建时间","会员来源","昵称",
                "真实姓名","使用状态","头像地址","QQ号码","账户余额","手机是否验证","邮箱是否检测",
                "性别","会员等级","积分","经验值","生日","","最后登录时间"};

        OutputStream out =null;
        //查出所有集合
        List<User> userList = userService1.findAll();


        try {

            String returnName =  new String("用户汇总表.xls".getBytes(), "ISO-8859-1");
            response.setContentType("application/octet-stream");
            response.setContentType("application/OCTET-STREAM;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + returnName);
            out= response.getOutputStream();
            ex.exportExcel(headers, userList,out);
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
