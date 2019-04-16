package cn.itcast.core.service;

public interface CollectService {
    void addItemIdToRedis(Long itemId,String name);
}
