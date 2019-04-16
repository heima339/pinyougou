package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 商品分类管理
 */
@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatDao itemCatDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<ItemCat> findByParentId(Long parentId) {

        //查询所有商品分类结果集 保存到缓存中
        List<ItemCat> itemCatList = itemCatDao.selectByExample(null);


        //遍历
        for (ItemCat itemCat : itemCatList) {

            //缓存 数据类型 Hash类型
            redisTemplate.boundHashOps("itemCatList").put(itemCat.getName(),itemCat.getTypeId());

        }
        //添加
        //修改
        //删除
        //redisTemplate.boundHashOps("itemCatList").delete(itemCat.getName())






        //从Mysql数据查询
        ItemCatQuery itemCatQuery = new ItemCatQuery();
        itemCatQuery.createCriteria().andParentIdEqualTo(parentId);
        return itemCatDao.selectByExample(itemCatQuery);
    }

    //查询一个
    @Override
    public ItemCat findOne(Long id) {
        return itemCatDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemCat> findAll() {
        return itemCatDao.selectByExample(null);
    }

    //查询商品分类结果集
    @Override
    public Map<String, Map> findItemCat() {


        //查询缓存
        Map<String, Map> itemCatMap = (Map<String, Map>) redisTemplate.boundHashOps("itemCatMap").entries();

        if (null == itemCatMap || itemCatMap.size() == 0) {
            //缓存没有数据查询数据库

//        查询一级分类
            ItemCatQuery itemCatQuery = new ItemCatQuery();
            itemCatQuery.createCriteria().andParentIdEqualTo((long) 0);
            List<ItemCat> itemCatList1 = itemCatDao.selectByExample(itemCatQuery);



            for (ItemCat itemCat1 : itemCatList1) {


                //根据一级分类id查询对应二级分类集合
                ItemCatQuery query1 = new ItemCatQuery();
                query1.createCriteria().andParentIdEqualTo(itemCat1.getId());
                List<ItemCat> itemCatList2 = itemCatDao.selectByExample(query1);


                ////定义map2 key为二级分类名称 v为二级分类所对应的三级分类名称集合
                Map<String, List> map2 = new HashMap<>();
                for (ItemCat itemCat2 : itemCatList2) {

                    //根据二级分类id查询对应三级分类集合
                    ItemCatQuery query2 = new ItemCatQuery();
                    query2.createCriteria().andParentIdEqualTo(itemCat1.getId());
                    List<ItemCat> itemCatList3 = itemCatDao.selectByExample(query2);
                    //定义List集合存放三级分类名称
                    List<String> list3 = new ArrayList<>();
                    for (ItemCat itemCat3 : itemCatList3) {
                        list3.add(itemCat3.getName());
                    }
                    map2.put(itemCat2.getName(), list3);
                }
                itemCatMap.put(itemCat1.getName(), map2);
                redisTemplate.boundHashOps("itemCatMap").put(itemCat1.getName(), map2);

            }
            //4:保存缓存一份  时间
            //redisTemplate.boundValueOps("itemCatMap").set(itemCatMap, -1, TimeUnit.HOURS);
        }


        return itemCatMap;
    }
    //使用原生mybatis查询  有条件的根据状态和名称查   并且是父id为0的
    @Override
    public PageResult search(Integer page, Integer rows, ItemCat itemCat) {
        PageHelper.startPage(page,rows);
        List<ItemCat> itemCatList =null;
        //如果名称不为空  则在查询之前 就在名称前加上百分号
        if(itemCat.getName()!=null && !"".equals(itemCat.getName())){

            String name = itemCat.getName();
            String realName = "%"+name+"%";
            itemCat.setName(realName);
            itemCatList =  itemCatDao.selectByParentIdAndStatusAndName(itemCat);
        }else {
            itemCatList = itemCatDao.selectByParentIdAndStatusAndName(itemCat);
        }

        Page<ItemCat> itemCatPage = (Page<ItemCat>) itemCatList;
        return new PageResult(itemCatPage.getTotal(),itemCatPage.getResult());
    }

    //添加分类
    @Override
    public void add(ItemCat itemCat) {

        itemCat.setItemCatStatus("0");
        itemCatDao.insertSelective(itemCat);



        //查询所有商品分类结果集 只有审核通过的才能保存到缓存中
        ItemCat itemCat2 = new ItemCat();
        itemCat2.setItemCatStatus("1");
        List<ItemCat> itemCatList = itemCatDao.selectByParentIdAndStatusAndName(itemCat2);


        //遍历
        for (ItemCat itemCat1 : itemCatList) {

            //缓存 数据类型 Hash类型
            redisTemplate.boundHashOps("itemCatList").put(itemCat1.getName(),itemCat1.getTypeId());

        }
    }


    //删除分类
    @Override
    public void delete(Long[] ids) {
        if(ids!=null && ids.length>0){
            for (Long id : ids) {
                //删除分类
                itemCatDao.deleteByPrimaryKey(id);
                //删除该分类下对应的子分类
                ItemCatQuery itemCatQuery = new ItemCatQuery();
                itemCatQuery.createCriteria().andParentIdEqualTo(id);
                itemCatDao.deleteByExample(itemCatQuery);

            }
        }

        //查询所有商品分类结果集 保存到缓存中
        ItemCat itemCat2 = new ItemCat();
        itemCat2.setItemCatStatus("1");
        List<ItemCat> itemCatList = itemCatDao.selectByParentIdAndStatusAndName(itemCat2);


        //遍历
        for (ItemCat itemCat1 : itemCatList) {

            //缓存 数据类型 Hash类型
            redisTemplate.boundHashOps("itemCatList").put(itemCat1.getName(),itemCat1.getTypeId());

        }
    }


    //修改并保存到数据库上
    @Override
    public void update(ItemCat itemCat) {
        itemCatDao.updateByPrimaryKeySelective(itemCat);

        //查询所有商品分类结果集 保存到缓存中
        ItemCat itemCat2 = new ItemCat();
        itemCat2.setItemCatStatus("1");
        List<ItemCat> itemCatList = itemCatDao.selectByParentIdAndStatusAndName(itemCat2);


        //遍历
        for (ItemCat itemCat1 : itemCatList) {

            //缓存 数据类型 Hash类型
            redisTemplate.boundHashOps("itemCatList").put(itemCat1.getName(),itemCat1.getTypeId());

        }
    }

    //将未审核的展示出来
    @Override
    public PageResult search1(Integer page, Integer rows, ItemCat itemCat) {
        PageHelper.startPage(page,rows);
        itemCat.setItemCatStatus("0");
        List<ItemCat> itemCatList =null;
        //如果名称不为空  则在查询之前 就在名称前加上百分号
        if(itemCat.getName()!=null && !"".equals(itemCat.getName())){

            String name = itemCat.getName();
            String realName = "%"+name+"%";
            itemCat.setName(realName);
            itemCatList =  itemCatDao.selectByParentIdAndStatusAndName(itemCat);
        }else {
            itemCatList = itemCatDao.selectByParentIdAndStatusAndName(itemCat);
        }
        Page<ItemCat> itemCatPage = (Page<ItemCat>) itemCatList;
        return new PageResult(itemCatPage.getTotal(),itemCatPage.getResult());

    }

    //页面已加载 就执行查询父id为0  并且状态为未审核的 数据
    @Override
    public List<ItemCat> findByParentId1(Long parentId) {
        ItemCat itemCat = new ItemCat();
        itemCat.setItemCatStatus("0");
        itemCat.setParentId(parentId);
        return itemCatDao.selectByParentIdAndStatusAndName(itemCat);
    }

    //修改审核状态
    @Override
    public void updateStatus(Long[] ids, String status) {
        if(ids!=null && ids.length>0){
            for (Long id : ids) {

                ItemCat itemCat = new ItemCat();
                itemCat.setItemCatStatus(status);
                itemCat.setId(id);
                itemCatDao.updateByPrimaryKeySelective(itemCat);
            }

        }

        //查询所有商品分类结果集 保存到缓存中
        ItemCat itemCat2 = new ItemCat();
        itemCat2.setItemCatStatus("1");
        List<ItemCat> itemCatList = itemCatDao.selectByParentIdAndStatusAndName(itemCat2);


        //遍历
        for (ItemCat itemCat1 : itemCatList) {


            //缓存 数据类型 Hash类型
            redisTemplate.boundHashOps("itemCatList").put(itemCat1.getName(),itemCat1.getTypeId());

        }



    }

    @Override
    public void deleteAll() {
        itemCatDao.deleteByExample(null);
    }

    @Override
    public void save(ItemCat itemCat) {
        itemCatDao.insertSelective(itemCat);
    }
}
