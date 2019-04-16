package cn.itcast.core.service;

import cn.itcast.core.pojo.item.Item;
import entity.PageResult;
import vo.OrderVo;

import java.util.List;

public interface MyOrderService {

    PageResult search(Integer page, Integer rows, String name, String status);

    List<Item> loadItemList(String name);
}
