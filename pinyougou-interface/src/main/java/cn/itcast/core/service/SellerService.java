package cn.itcast.core.service;

import cn.itcast.core.pojo.seller.Seller;
import entity.PageResult;

public interface SellerService {
    void add(Seller seller);

    Seller findBySellerId(String username);

    PageResult search(Seller seller, Integer page, Integer rows);

    Seller findOne(String id);

    void updateStatus(String sellerId, String status);
}
