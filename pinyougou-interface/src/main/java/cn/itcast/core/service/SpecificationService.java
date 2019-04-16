package cn.itcast.core.service;

import cn.itcast.core.pojo.specification.Specification;
import entity.PageResult;
import vo.SpecificationVo;

import java.util.List;
import java.util.Map;

public interface SpecificationService {
    PageResult search(Integer page, Integer rows, Specification specification);

    void add(SpecificationVo specificationVo);

    SpecificationVo findOne(Long id);

    void update(SpecificationVo specificationVo);

    List<Map> selectOptionList();

    void updateStatus(Long[] ids, String status);

    void delete(Long[] ids);

    PageResult search1(Integer page, Integer rows, Specification specification);

    void save(Specification specification);

    void deleteAll();
}
