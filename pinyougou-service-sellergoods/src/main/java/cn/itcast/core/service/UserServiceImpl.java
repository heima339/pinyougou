package cn.itcast.core.service;

import cn.itcast.core.dao.user.UserDao;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.pojo.user.UserQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService1 {

    @Autowired
    private UserDao userDao;

    //搜索
    @Override
    public PageResult search(Integer page, Integer rows, User user) {
        PageHelper.startPage(page,rows);
        UserQuery userQuery = new UserQuery();
        UserQuery.Criteria criteria = userQuery.createCriteria();
        if(user.getUsername()!=null  && !"".equals(user.getUsername())){
            criteria.andUsernameLike(user.getUsername());
        }
        if(user.getStatus()!=null && !"".equals(user.getStatus())){
            criteria.andStatusEqualTo(user.getStatus());
        }
        Page<User> userPage = (Page<User>) userDao.selectByExample(userQuery);
        return new PageResult(userPage.getTotal(),userPage.getResult());
    }

    //查询一个用户user
    @Override
    public User findOne(Long id) {
        if(id!=null){
            return userDao.selectByPrimaryKey(id);
        }
        return null;
    }


    //改变用户的状态
    @Override
    public void updateStatus(Long id, String status) {
        if(id!=null){
            User user = new User();
            user.setId(id);
            user.setStatus(status);
            userDao.updateByPrimaryKeySelective(user);
        }

    }

    @Override
    public List<User> findAll() {
        List<User> users = userDao.selectByExample(null);

        return users;
    }
}
