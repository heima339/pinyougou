package cn.itcast.core.service;

import cn.itcast.core.pojo.item.ItemCat;

import java.util.List;
import java.util.Map;

public interface ItemCatService {
    List<ItemCat> findByParentId(Long parentId);

    ItemCat findOne(Long id);

    List<ItemCat> findAll();

    void add(ItemCat itemCat);

    List<Map> selectOptionList();
}
