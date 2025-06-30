package cn.rj.hyhealthbackend.model;

import cn.rj.hyhealthbackend.domain.MedicalPolicy;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 陈亮
 * <p>
 * 医保政策数据模型
 */
@Getter
@Setter
public class MedicalPolicyModel extends MedicalPolicy {

    private CityModel cityModel;  // 医保政策包含的所属城市
}

