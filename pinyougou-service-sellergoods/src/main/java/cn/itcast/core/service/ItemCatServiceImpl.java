package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatQuery;
import com.alibaba.dubbo.config.annotation.Service;
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
}
