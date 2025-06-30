package cn.rj.hyhealthbackend.mapper;

import cn.rj.hyhealthbackend.model.CompanyPolicyModel;
import cn.rj.hyhealthbackend.param.CompanyPolicyParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 陈亮
 * <p>
 * CompanyPolicyMapper接口
 * - 添加通过查询参数查询医药公司政策的方法
 */
@Component
public interface CompanyPolicyMapper {

    // 查询所有的医药公司政策
    List<CompanyPolicyModel> getAllPolicy(CompanyPolicyParam param);
}
