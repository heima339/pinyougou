package cn.itcast.core.service;

import cn.itcast.core.pojo.good.Brand;
import entity.PageResult;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BrandService {

    List<Brand> findAll();

    PageResult findPage(Integer pageNo, Integer pageSize);

    void add(Brand brand);

    Brand findOne(Long id);

    void update(Brand brand);

    void delete(Long[] ids);

    PageResult search(Integer pageNo, Integer pageSize, Brand brand);

    List<Map> selectOptionList();


    void save(Brand brand);

    void deleteAll();
}
