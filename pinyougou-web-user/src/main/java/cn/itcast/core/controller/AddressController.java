package cn.itcast.core.controller;

import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.AddressService;
import com.alibaba.dubbo.config.annotation.Reference;

import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("address")
/**
 * 地址管理
 */
public class AddressController {

    @Reference
    private AddressService addressService;

    //"../address/findAll.do"
    @RequestMapping("findListByLoginUser")
    public List<Address> findListByLoginUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return addressService.findListByLoginUser(name);
    }

    //新增地址
    @RequestMapping("addAddress")
    public Result addAddress(@RequestBody Address address) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        address.setUserId(name);
        address.setCreateDate(new Date());
        //设置是否默认
        address.setIsDefault("0");
        try {
            addressService.addAddress(address);
            return new Result(true, "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存失败");
        }

    }
    @RequestMapping("update")
    public Result update(@RequestBody Address address) {

        address.setCreateDate(new Date());
        try {
            addressService.update(address);
            return new Result(true, "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存失败");
        }

    }
    //get("../address/delete.do?id="+id);
    //删除地址
    @RequestMapping("delete")
    public Result delete(Long id) {

        try {
            addressService.delete(id);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }

    }
    //设为默认地址
    //get("../address/setDefault.do?id="+id);
    @RequestMapping("setDefault")
    public Result setDefault(Long id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            addressService.setDefault(id,name);
            return new Result(true, "设置成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "设置失败");
        }

    }

    //get("../address/findOneById.do?id="+id);
    //根据id查询一个地址
    @RequestMapping("findOneById")
    public Address findOneById(Long id) {
        return addressService.findOneById(id);
    }

}
