package cn.itcast.core.service;

import cn.itcast.core.pojo.user.User;

import java.util.Map;

public interface UserService {
    void sendCode(String phone);

    void add(String smscode, User user);

    Map countUser();
}
