package cn.rj.hyhealthbackend.domain;


import cn.rj.hyhealthbackend.domain.superdomain.SuperDomain;
import lombok.Data;

/**
 * @author 李柯嘉
 *
 * 诊治类别实体类
 * 诊治类型id以及诊治类型名称name
 */
@Data
public class TreatType extends SuperDomain {
    private Long id;//诊治类型id
    private String name;//诊治类型名称
}
