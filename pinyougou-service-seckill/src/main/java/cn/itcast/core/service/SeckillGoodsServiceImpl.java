package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
@Autowired
   private RedisTemplate redisTemplate;
    @Override
    public List<SeckillGoods> findList() {
        List<SeckillGoods> seckillGoodsList = redisTemplate.boundHashOps("seckill").values();
        /* Map seckillGoods1 = redisTemplate.boundHashOps("seckillGoods").entries();*/
        if (null!=seckillGoodsList&&seckillGoodsList.size()>0){
                   return  seckillGoodsList;
        }
        SeckillGoodsQuery seckillGoodsQuery = new SeckillGoodsQuery();
        SeckillGoodsQuery.Criteria criteria = seckillGoodsQuery.createCriteria();
        criteria.andStatusEqualTo("1");//审核通过
        criteria.andStockCountGreaterThan(0);//剩余库存大于0
        criteria.andStartTimeLessThanOrEqualTo(new Date());//开始时间小于等于当前时间
        criteria.andEndTimeGreaterThan(new Date());//结束时间大于当前时间 
        List<SeckillGoods> seckillGoods = seckillGoodsDao.selectByExample(null);
        for (SeckillGoods seckillGood : seckillGoods) {
            redisTemplate.boundHashOps("seckill").put(seckillGood.getId(),seckillGood);
        }
        return seckillGoods;
    }

    @Override
    public SeckillGoods findOneFromRedis(Long id) {
        SeckillGoods seckill = (SeckillGoods)redisTemplate.boundHashOps("seckill").get(id);
         return  seckill;
        /*SeckillGoods seckillGoods = seckillGoodsDao.selectByPrimaryKey(id);*/

    }
}
