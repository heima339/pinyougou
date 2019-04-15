package cn.itcast.core.task;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *      * 刷新秒杀商品
 *      
 */
@Component
public class SeckillTask {
@Autowired
private RedisTemplate redisTemplate;
@Autowired
private SeckillGoodsDao seckillGoodsDao;
    /**
     * 刷新秒杀商品
     */
    @Scheduled(cron="* * * * * ?")
    public void refreshSeckillGoods() {
        System.out.println("执行了任务调度"+new Date());
        //查询所有的秒杀商品键集合
        List ids = new ArrayList(redisTemplate.boundHashOps("seckill").keys());
        //查询正在秒杀的商品列表
        SeckillGoodsQuery seckillGoodsQuery = new SeckillGoodsQuery();
        SeckillGoodsQuery.Criteria criteria = seckillGoodsQuery.createCriteria();
        criteria.andStatusEqualTo("1");//审核通过
        criteria.andStockCountGreaterThan(0);//剩余库存大于0
        criteria.andStartTimeLessThanOrEqualTo(new Date());//开始时间小于等于当前时间
        criteria.andEndTimeGreaterThan(new Date());//结束时间大于当前时间 
            if (ids.size()>0){

            criteria.andIdNotIn(ids);//排除缓存中已经有的商品
        }

        //查询符合条件的秒杀商品
        List<SeckillGoods> seckillGoodsList = seckillGoodsDao.selectByExample(seckillGoodsQuery);
        // 缓存中
        for (SeckillGoods seckillGoods : seckillGoodsList) {
            redisTemplate.boundHashOps("seckill").put(seckillGoods.getId(),seckillGoods);
        }
        System.out.println("将"+seckillGoodsList.size()+"条新秒杀商品装入缓存");
    }





    /**
     *每秒中在缓存的秒杀商品列表中查询过期的商品
     * 发现过期同步到数据库
     * 在缓存中移除该秒杀商品
     * 移除秒杀商品
     */
    @Scheduled(cron="* * * * * ?")
    public void  removeSeckillGoods(){
        System.out.println("移除秒杀商品任务在执行");
        //扫描缓存中秒杀商品列表，发现过期的移除
        List<SeckillGoods> seckilllistout = redisTemplate.boundHashOps("seckill").values();
        for (SeckillGoods seckillGood: seckilllistout) {
            //如果秒杀商品的活动结束时间小于当前时间说明秒杀活动结束
            System.out.println(seckillGood.getEndTime().getTime());
            System.out.println(new Date().getTime());
           if (seckillGood.getEndTime().getTime()<new Date().getTime()){
              /* seckillGoodsDao.updateByPrimaryKey(seckillGood);//向数据库保存记录*/
               System.out.println("移除秒杀商品移除秒杀商品移除秒杀商品移除秒杀商品移除秒杀商品"+seckillGood.getId());
               redisTemplate.boundHashOps("seckill").delete(seckillGood.getId());//移除缓存数据
           }
        }
        System.out.println("移除秒杀商品任务结束");
    }

}
