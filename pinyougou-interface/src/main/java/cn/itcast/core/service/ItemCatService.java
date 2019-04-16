package cn.itcast.core.service;

import cn.itcast.core.pojo.item.ItemCat;
import entity.PageResult;

import java.util.List;
import java.util.Map;

public interface ItemCatService {
    List<ItemCat> findByParentId(Long parentId);

    ItemCat findOne(Long id);

    List<ItemCat> findAll();

    PageResult search(Integer page, Integer rows, ItemCat itemCat);

    void add(ItemCat itemCat);

    void delete(Long[] ids);

    void update(ItemCat itemCat);

    PageResult search1(Integer page, Integer rows, ItemCat itemCat);

    List<ItemCat> findByParentId1(Long parentId);

    void updateStatus(Long[] ids, String status);

    void deleteAll();

    void save(ItemCat itemCat);

    Map<String, Map> findItemCat();

}
