package cn.itcast.core.controller;

import cn.itcast.core.service.UserService;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;
    @RequestMapping("/countUser")
    public Map countUser(){
        return userService.countUser();
    }
}
