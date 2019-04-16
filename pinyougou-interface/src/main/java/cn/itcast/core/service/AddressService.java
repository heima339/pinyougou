package cn.itcast.core.service;

import cn.itcast.core.pojo.address.Address;

import java.util.List;

public interface AddressService {
    List<Address> findListByLoginUser(String name);


    void addAddress(Address address);

    void delete(Long id);

    void setDefault(Long id, String name);

    Address findOneById(Long id);

    void update(Address address);
}
