package cn.itcast.core.service;

import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.pojo.seller.SellerQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商家管理
 */
@Service
@Transactional
public class SellerServiceImpl implements  SellerService {


    @Autowired
    private SellerDao sellerDao;
    //添加
    @Override
    public void add(Seller seller) {

        //判断商家名称是否为null
        if(null != seller.getSellerId() && !"".equals(seller.getSellerId())) {
            //判断商家名称是否含有空格
            if(!seller.getSellerId().contains(" ")){

                //保存
                 seller.setPassword(new BCryptPasswordEncoder().encode(seller.getPassword()));
                 //默认是未审核
                seller.setStatus("0");
                sellerDao.insertSelective(seller);

            }else{
                throw new RuntimeException("商家名称不能有空格");
            }

        }else{
            throw new RuntimeException("商家名称不能为空");
        }

    }

    //根据用户名查询一个商家 要求此商家必须审核通过
    @Override
    public Seller findBySellerId(String username) {

        return sellerDao.selectByPrimaryKey(username);
    }

    @Override
    public PageResult search(Seller seller, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        SellerQuery sellerQuery = new SellerQuery();
        SellerQuery.Criteria criteria = sellerQuery.createCriteria();
        if (seller != null) {
            if (seller.getStatus() != null && !"".equals(seller.getStatus().trim())) {
                criteria.andStatusEqualTo(seller.getStatus());
            }
            if (seller.getName() != null && !"".equals(seller.getName().trim())) {
                criteria.andNameLike("%"+seller.getName()+"%");
            }
            if (seller.getNickName() != null && !"".equals(seller.getNickName().trim())) {
                criteria.andNameLike("%"+seller.getNickName()+"%");
            }
        }
        Page<Seller> sellers = (Page<Seller>) sellerDao.selectByExample(sellerQuery);
        return new PageResult(sellers.getTotal(), sellers.getResult());
    }

    @Override
    public Seller findOne(String id) {
        Seller seller = sellerDao.selectByPrimaryKey(id);
        return seller;
    }

    @Override
    public void updateStatus(String sellerId, String status) {
        Seller seller = new Seller();
        seller.setSellerId(sellerId);
        seller.setStatus(status);
        sellerDao.updateByPrimaryKeySelective(seller);
    }
}
