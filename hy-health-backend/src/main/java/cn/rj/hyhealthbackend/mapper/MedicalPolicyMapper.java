package cn.rj.hyhealthbackend.mapper;

import cn.rj.hyhealthbackend.entity.MedicalPolicyEntity;
import cn.rj.hyhealthbackend.model.MedicalPolicyModel;
import cn.rj.hyhealthbackend.param.MedicalPolicyParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 陈亮
 * <p>
 * 医保政策数据库接口
 * - 添加医保政策分页查询方法，通过医保政策查询参数查询医保政策
 * - 医保政策添加方法、医保政策修改方法、医保政策删除方法以及根据城市删除医保政策方法
 */
@Component
public interface MedicalPolicyMapper {

    // 医保政策条件查询
    List<MedicalPolicyModel> getAllPolicy(MedicalPolicyParam param);

    // 增医保政策
    int saveMedicalPolicy(MedicalPolicyEntity param);

    // 更新医保政策
    int updateMedicalPolicy(MedicalPolicyEntity param);

    // 根据id删除医保政策
    int deleteMedicalPolicy(Long id);

    // 根据城市删除医保政策
    int deleteByCity(Integer city);
}
