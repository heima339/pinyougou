package cn.itcast.core.controller;

import cn.itcast.common.utils.FastDFSClient;
import cn.itcast.common.utils.PhoneFormatCheckUtils;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;

import entity.Result;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/user")
public class UserController {


    //图片服务器ＵＲＬ
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;
    @Reference
    private UserService userService;

    //发短信验证码
    @RequestMapping("/sendCode")
    public Result sendCode(String phone) {

        try {
            //合法
            if (PhoneFormatCheckUtils.isPhoneLegal(phone)) {
                //发短信 验证码 Service
                userService.sendCode(phone);
            } else {
                return new Result(false, "手机不合法");
            }
            return new Result(true, "获取验证码成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "获取验证码失败");
        }
    }

    //添加
    @RequestMapping("/add")
    public Result add(String smscode, @RequestBody User user) {
        try {
            userService.add(smscode, user);
            return new Result(true, "注册成功");
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "注册失败");
            //预期      运行时
        }

    }

    //查询
    @RequestMapping("/findUser")
    public User findUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return  userService.findUser(name);
    }

    //图片上传

    //上传商品图片
    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file){ //配置一个实现类


        //接收图片
        try {
            System.out.println(file.getOriginalFilename());

            //上传分布式文件系统 FastDFS
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");

            //扩展名
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());

            //上传图片
            // group1/M00/00/01/wKjIgFWOYc6APpjAAAD-qk29i78248.jpg
             String path = fastDFSClient.uploadFile(file.getBytes(), ext, null);
            String path1=FILE_SERVER_URL + path;
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            userService.save(path1,name);
            return new Result(true,path1);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }

    }





    @RequestMapping("/update")
    public  Result update(@RequestBody User user){

        try {
            userService.update(user);
            return new Result(true, "修改成功");
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
            //预期      运行时
        }
    }
}
