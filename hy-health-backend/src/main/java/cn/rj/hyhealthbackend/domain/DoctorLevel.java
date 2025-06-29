package cn.rj.hyhealthbackend.domain;

import cn.rj.hyhealthbackend.domain.superdomain.SuperDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 梁旭
 *
 * 医师级别实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DoctorLevel extends SuperDomain {
    private Long id;//级别id
    private String name;//级别名称
}
