package cn.itcast.core.service;

import cn.itcast.core.dao.address.AddressDao;
import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.address.AddressQuery;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收货地址管理
 */
@Service
@Transactional
public class AddressServiceImpl implements  AddressService {

    @Autowired
    private AddressDao addressDao;
    @Override
    public List<Address> findListByLoginUser(String name) {
        AddressQuery addressQuery = new AddressQuery();
        addressQuery.createCriteria().andUserIdEqualTo(name);
        return addressDao.selectByExample(addressQuery);
    }

    @Override
    public void addAddress(Address address) {
        addressDao.insertSelective(address);
    }

    @Override
    public void delete(Long id) {
        addressDao.deleteByPrimaryKey(id);
    }

    @Override
    public void setDefault(Long id, String name) {
        //将用户所有地址全部设为不默认
        AddressQuery addressQuery = new AddressQuery();
        addressQuery.createCriteria().andUserIdEqualTo(name);
        Address address = new Address();
        address.setIsDefault("0");
        addressDao.updateByExampleSelective(address, addressQuery);

        //根据地址id设为默认
        address.setId(id);
        address.setIsDefault("1");
        addressDao.updateByPrimaryKeySelective(address);
    }

    @Override
    public Address findOneById(Long id) {
        return addressDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(Address address) {
        addressDao.updateByPrimaryKeySelective(address);
    }


}
