package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
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

    @Override
    public PageResult search(Integer page, Integer rows, SeckillGoods seckillGoods) {
        //分页插件
        PageHelper.startPage(page, rows);

        //分页插件 排序
        //1:办法：PageHelper.orderBy("id desc");
        //

        //条件 对象
        SeckillGoodsQuery seckillGoodsQuery = new SeckillGoodsQuery();
        SeckillGoodsQuery.Criteria criteria = seckillGoodsQuery.createCriteria();

        //排序
        seckillGoodsQuery.setOrderByClause("id desc");

        //判断 状态  运营商后台 默认查询所有状态
        if (null != seckillGoods.getStatus() && !"".equals(seckillGoods.getStatus())) {
            criteria.andStatusEqualTo(seckillGoods.getStatus());
        }
        //商品名称
        if (null != seckillGoods.getTitle() && !"".equals(seckillGoods.getTitle().trim())) {
            criteria.andTitleLike("%" + seckillGoods.getTitle().trim() + "%");
        }

        //判断当前查询的是商家后台 还是运营商后台
        if (null != seckillGoods.getSellerId()) {
            //商家查询
            //只能查询自己家的商品
            criteria.andSellerIdEqualTo(seckillGoods.getSellerId());
        }
        //运营商查询  没有商家ID 查询所有商家的

        //只查询不删除
        //criteria.andIsDeleteIsNull();

        Page<SeckillGoods> p = (Page<SeckillGoods>) seckillGoodsDao.selectByExample(seckillGoodsQuery);
        //ArrayList arrayList = new ArrayList();//Ctrl + alt + V
        //select * from tb_goods where ....  order by id desc limit 开始行,每页数  Mybatis -- dataSource -- Mysql

        return new PageResult(p.getTotal(), p.getResult());
    }
}
