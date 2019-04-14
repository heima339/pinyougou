package cn.itcast.core.service;


import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    /**
     * 分页查询所有秒杀商品
     * @param pageNo
     * @param rows
     * @param goods
     * @return
     */
    @Override
    public PageResult search(Integer pageNo, Integer rows, SeckillGoods goods) {
        PageHelper.startPage(pageNo,rows);
        SeckillGoodsQuery query = new SeckillGoodsQuery();
        if (goods.getStatus() != null && !"".equals(goods.getStatus().trim())){
            query.createCriteria().andStatusEqualTo(goods.getStatus());
        }
        if (goods.getTitle()!=null &&  !"".equals(goods.getTitle().trim())){
            query.createCriteria().andTitleLike("%"+goods.getTitle()+"%");
        }
        if (goods.getSellerId()!=null &&  !"".equals(goods.getSellerId().trim())){
            query.createCriteria().andSellerIdEqualTo(goods.getSellerId());
        }
        Page<SeckillGoods> page = (Page<SeckillGoods>) seckillGoodsDao.selectByExample(query);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 根据id查询秒杀商品
     * @param id
     * @return
     */
    @Override
    public SeckillGoods findOne(Long id) {
        return seckillGoodsDao.selectByPrimaryKey(id);
    }

    /**
     * 根据id修改秒杀商品状态
     * 0---未审核
     * 1---审核通过
     * 2---审核不通过
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, String status) {
        SeckillGoods seckillGoods = new SeckillGoods();
        seckillGoods.setId(id);
        seckillGoods.setStatus(status);
        seckillGoodsDao.updateByPrimaryKeySelective(seckillGoods);
    }

    /**
     * 根据id删除秒杀商品
     * @param id
     */
    @Override
    public void delete(Long id) {
        seckillGoodsDao.deleteByPrimaryKey(id);
    }
}
