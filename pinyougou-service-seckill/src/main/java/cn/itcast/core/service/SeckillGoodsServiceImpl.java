package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    //申请
    @Override
    public void add(SeckillGoods seckillGoods) {
        seckillGoodsDao.insertSelective(seckillGoods);
    }
}
