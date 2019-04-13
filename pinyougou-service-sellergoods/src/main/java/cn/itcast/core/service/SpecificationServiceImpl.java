package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationDao;
import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.pojo.specification.SpecificationOptionQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vo.SpecificationVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 规格管理
 */
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecificationDao specificationDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;
    //@Autowired
    //private SqlSessionFactory sqlSessionFactory;

    //查询分页 有条件
    @Override
    public PageResult search(Integer page, Integer rows, Specification specification) {
        //插件
        PageHelper.startPage(page,rows);

        //使用原生myBatis查询数据库
        ArrayList<Specification> specifications = null;
        if(specification.getSpecName()!=null && !"".equals(specification.getSpecName())){
            String specName = specification.getSpecName();
            String realSpecName = "%"+specName+"%";
            specification.setSpecName(realSpecName);
            specifications = (ArrayList<Specification>) specificationDao.selectByStatusAndSpecName(specification);
        }else {
            specifications = (ArrayList<Specification>) specificationDao.selectByStatusAndSpecName(specification);

        }


        Page<Specification> p = (Page<Specification>) specifications;


        return new PageResult(p.getTotal(),p.getResult());
    }

    //添加
    @Override
    public void add(SpecificationVo specificationVo) {

        //加入新规格  将规格状态改为0
        Specification specification =specificationVo.getSpecification();
        specification.setSpecStatus("0");
        //规格表
        specificationDao.insertSelective(specification);

        //规格选项表 多
        List<SpecificationOption> specificationOptionList = specificationVo.getSpecificationOptionList();
        for (SpecificationOption specificationOption : specificationOptionList) {
            //外键
            specificationOption.setSpecId(specificationVo.getSpecification().getId());
            specificationOptionDao.insertSelective(specificationOption);
        }

    }

    //查询一个规格
    @Override
    public SpecificationVo findOne(Long id) {



        SpecificationVo vo = new SpecificationVo();
        //规格
        vo.setSpecification(specificationDao.selectByPrimaryKey(id));
        //规格选项结果集
        SpecificationOptionQuery query = new SpecificationOptionQuery();
        query.createCriteria().andSpecIdEqualTo(id);
        vo.setSpecificationOptionList(specificationOptionDao.selectByExample(query));
        return vo;
    }

    //修改

    @Override
    public void update(SpecificationVo specificationVo) {



        //规格表 修改
        specificationDao.updateByPrimaryKeySelective(specificationVo.getSpecification());
        //规格选项表
        //1:删除  通过外键 批量删除
        SpecificationOptionQuery query = new SpecificationOptionQuery();
        query.createCriteria().andSpecIdEqualTo(specificationVo.getSpecification().getId());
        specificationOptionDao.deleteByExample(query);
        //2:添加
        List<SpecificationOption> specificationOptionList = specificationVo.getSpecificationOptionList();
        for (SpecificationOption specificationOption : specificationOptionList) {
            //外键
            specificationOption.setSpecId(specificationVo.getSpecification().getId());
            specificationOptionDao.insertSelective(specificationOption);
        }

    }

    @Override
    public List<Map> selectOptionList() {



        return specificationDao.selectOptionList();
    }

    //修改状态
    @Override
    public void updateStatus(Long[] ids, String status) {
        if(ids!=null &&ids.length>0){
            for (Long id : ids) {

                specificationDao.updateStatus(id,status);
            }
        }
    }


    //删除规格
    @Override
    public void delete(Long[] ids) {
        if(ids!=null && ids.length>0){
            for (Long id : ids) {

                specificationDao.deleteByPrimaryKey(id);
            }
        }

    }

    @Override //将未审核的查出来
    public PageResult search1(Integer page, Integer rows, Specification specification) {
        //插件
        PageHelper.startPage(page,rows);

        //使用原生myBatis查询数据库
        specification.setSpecStatus("0");
        //使用原生myBatis查询数据库
        ArrayList<Specification> specifications = null;

        if(specification.getSpecName()!=null && !"".equals(specification.getSpecName())){
            String specName = specification.getSpecName();
            String realSpecName = "%"+specName+"%";
            specification.setSpecName(realSpecName);
            specifications = (ArrayList<Specification>) specificationDao.selectByStatusAndSpecName(specification);
        }else {
            specifications = (ArrayList<Specification>) specificationDao.selectByStatusAndSpecName(specification);

        }


        Page<Specification> p = (Page<Specification>) specifications;


        return new PageResult(p.getTotal(),p.getResult());
    }
}
