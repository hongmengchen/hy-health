package cn.rj.hyhealthbackend.mapper;

import cn.rj.hyhealthbackend.domain.DrugCompany;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 陈亮
 * <p>
 * 医药公司数据库操作接口
 * 添加根据name模糊查询医药公司信息方法以及根据id查询医药公司信息方法
 */
@Component
public interface CompanyMapper {

    // 获取所有医药公司信息,name 不为空则模糊查询
    List<DrugCompany> getAllCompany(String name);

    // 根据 id 查找一个医药公司信息
    DrugCompany getCompanyById(Integer id);

}
