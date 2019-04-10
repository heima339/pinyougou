package cn.itcast.core.controller;

import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.service.SellerService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("seller")
public class SellerController {
    @Reference
    private SellerService sellerService;

    /**
     * 高级搜索  分页展示
     * @param seller
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("search")
    public PageResult search(@RequestBody Seller seller, Integer page, Integer rows) {
        PageResult pageResult = sellerService.search(seller, page, rows);
        return pageResult;
    }

    /**
     * 数据回显
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public Seller findOne(String id) {
        Seller seller=sellerService.findOne(id);
        return seller;

    }

    @RequestMapping("updateStatus")
    public Result updateStatus(String sellerId, String status) {
        try {
            sellerService.updateStatus(sellerId, status);
            return new Result(true, "更改状态成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "更改状态失败");
        }
    }
}
