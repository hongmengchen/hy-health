package cn.rj.hyhealthbackend.mapper;

import cn.rj.hyhealthbackend.entity.CompanyPolicyEntity;
import cn.rj.hyhealthbackend.model.CompanyPolicyModel;
import cn.rj.hyhealthbackend.param.CompanyPolicyParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 陈亮
 * <p>
 * CompanyPolicyMapper接口
 * - 添加通过查询参数查询医药公司政策的方法
 * - 新增医药公司政策，更新医药公司政策以及删除医药公司政策方法，其中删除医药公司政策方法包括通过政策id删除和通过公司id删除
 */
@Component
public interface CompanyPolicyMapper {

    // 查询所有的医药公司政策
    List<CompanyPolicyModel> getAllPolicy(CompanyPolicyParam param);

    // 新增医药公司政策
    int savePolicy(CompanyPolicyEntity entity);

    // 更新医药公司政策
    int updatePolicy(CompanyPolicyEntity entity);

    // 删除医药公司政策
    int deletePolicy(Long id);

    int deletePolicyByCompany(Integer id);
}
