package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.pojo.seckill.SeckillOrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class seckillOrderServiceImpl implements seckillOrderService{
    @Autowired
    private SeckillOrderDao seckillOrderDao;
    @Override
    public List<SeckillOrder> findAll() {
        List<SeckillOrder> seckillOrders = seckillOrderDao.selectByExample(null);
        return seckillOrders;
    }

    @Override
    public PageResult search(Integer pageNo, Integer pageSize, SeckillOrder seckillOrder) {
        //Mybatis分页插件
        PageHelper.startPage(pageNo,pageSize);
        //条件对象
        SeckillOrderQuery seckillOrderQuery = new SeckillOrderQuery();
        SeckillOrderQuery.Criteria criteria = seckillOrderQuery.createCriteria();
        if (null != seckillOrder.getId() && !"".equals(seckillOrder.getId())) {
            criteria.andIdEqualTo(seckillOrder.getId());
        }
        if (null != seckillOrder.getSellerId() && !"".equals(seckillOrder.getSellerId())) {
            criteria.andSellerIdEqualTo(seckillOrder.getSellerId());
        }
        if (null != seckillOrder.getSeckillId() && !"".equals(seckillOrder.getSeckillId())) {
             criteria.andSeckillIdEqualTo(seckillOrder.getSeckillId());
        }


        //分页对象
        Page<SeckillOrder> page = (Page<SeckillOrder>) seckillOrderDao.selectByExample(seckillOrderQuery);
        for (SeckillOrder order : page.getResult()) {
            order.setIdStr(String.valueOf(order.getId()));
        }
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public SeckillOrder findOne(Long id) {
        SeckillOrder seckillOrder = seckillOrderDao.selectByPrimaryKey(id);
        seckillOrder.setIdStr(String.valueOf(seckillOrder.getId()));
        return seckillOrder;
    }
}
