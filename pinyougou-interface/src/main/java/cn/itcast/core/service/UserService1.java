package cn.itcast.core.service;

import cn.itcast.core.pojo.user.User;
import entity.PageResult;

import java.util.List;

/**
 * 用于用户管理
 */
public interface UserService1 {
    PageResult search(Integer page, Integer rows, User user);


   User findOne(Long id) ;

    void updateStatus(Long id, String status);

    List<User> findAll();
}
