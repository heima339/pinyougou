package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.pojo.seckill.SeckillOrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private SeckillOrderDao seckillOrderDao;

    //查询
    @Override
    public PageResult search(Integer page, Integer rows, SeckillOrder seckillOrder) {
        //分页插件
        PageHelper.startPage(page, rows);

        //条件 对象
        SeckillOrderQuery seckillOrderQuery = new SeckillOrderQuery();
        SeckillOrderQuery.Criteria criteria = seckillOrderQuery.createCriteria();

        //排序
        seckillOrderQuery.setOrderByClause("id desc");

        //判断 状态  运营商后台 默认查询所有状态
        if (null != seckillOrder.getStatus() && !"".equals(seckillOrder.getStatus())) {
            criteria.andStatusEqualTo(seckillOrder.getStatus());
        }


        //判断当前查询的是商家后台 还是运营商后台
        if (null != seckillOrder.getSellerId()) {
            //商家查询
            //只能查询自己家的商品
            criteria.andSellerIdEqualTo(seckillOrder.getSellerId());
        }
        //运营商查询  没有商家ID 查询所有商家的


        Page<SeckillOrder> p = (Page<SeckillOrder>) seckillOrderDao.selectByExample(seckillOrderQuery);
        return new PageResult(p.getTotal(), p.getResult());

    }
}
