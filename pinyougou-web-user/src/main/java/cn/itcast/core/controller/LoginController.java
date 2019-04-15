package cn.itcast.core.controller;

import cn.itcast.core.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Reference
    private UserService userService;
    //获取当前登陆人
    @RequestMapping("/name")
    public Map<String,Object> name(){
        //1:当前线程中的SecurityContext
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String,Object> map = new HashMap<>();
        map.put("loginName",name);
       // map.put("curTime",new Date());
        String path= userService.touxinag(name);
        map.put("touxiang",path);
        return map;
    }
}
