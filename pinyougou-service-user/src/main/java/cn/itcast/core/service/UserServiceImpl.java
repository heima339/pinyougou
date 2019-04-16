package cn.itcast.core.service;

import cn.itcast.common.utils.DateUtils;
import cn.itcast.core.dao.user.UserDao;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.pojo.user.UserQuery;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户管理
 */
@Service
@Transactional
public class UserServiceImpl implements  UserService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination smsDestination;


    /**
     * 统计可用用户数量 可用状态Y 不可用N
     * 活跃用户数量(近30天有登录记录的)
     * 非活跃用户数量(近30天没有登录记录的)
     * 总用户数量
     * @return
     */
    @Override
    public Map countUser() {
        Map<String, Integer> map = new HashMap<>();
        //查询总用户数量
        UserQuery query = new UserQuery();
        int totalUser = userDao.countByExample(query);
        map.put("totalUser",totalUser);

        //查询活跃用户数量
        //获取当前时间
        Date date = new Date();
        //获的前第30天的日期
        Date preDay = DateUtils.dateAddOrSubtract(date, -30);
        UserQuery userQuery = new UserQuery();
        userQuery.createCriteria().andLastLoginTimeBetween(preDay,date);
        userQuery.createCriteria().andStatusEqualTo("Y");
        int activeUserNum = userDao.countByExample(userQuery);
        map.put("activeUserNum",activeUserNum);
        map.put("inActiveUserNum",(totalUser-activeUserNum));
        return  map;
    }
    //发短信
    @Override
    public void sendCode(String phone){
        //1:生成6位验证码
        String randomNumeric = RandomStringUtils.randomNumeric(6);
        //2:保存缓存一份
        redisTemplate.boundValueOps(phone).set(randomNumeric,5, TimeUnit.HOURS);

        //3:发消息
        jmsTemplate.send(smsDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("SignName","品优购商城");
                mapMessage.setString("TemplateCode","SMS_126462276");
                mapMessage.setString("TemplateParam","{\"number\":\""+randomNumeric+"\"}");
                mapMessage.setString("PhoneNumbers",phone);//17630593193

                return mapMessage;
            }
        });


    }

    @Autowired
    private UserDao userDao;
    //用户添加
    @Override
    public void add(String smscode, User user) {
        String code = (String) redisTemplate.boundValueOps(user.getPhone()).get();
        if(null != code){
        //1：判断验证码是否失效
            if(code.equals(smscode)){
        //2:判断验证码是否正确
                //3:先加密密码 再保存用户
                user.setCreated(new Date());
                user.setUpdated(new Date());
                userDao.insertSelective(user);
            }else{
                throw new RuntimeException("验证码错误");
            }

        }else{
            throw new RuntimeException("验证码失败");
        }



    }

}
