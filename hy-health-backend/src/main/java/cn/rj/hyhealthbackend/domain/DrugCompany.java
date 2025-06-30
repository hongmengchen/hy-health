package cn.rj.hyhealthbackend.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 陈亮
 * <p>
 * 医药公司实体类
 * 医药公司属性有公司id，公司名，公司电话，更新时间以及创建时间
 */
@Getter
@Setter
public class DrugCompany {
    private Long companyId;  // 公司 id
    private String companyName;  // 公司名
    private String companyPhone;  // 公司电话
    private Date updatetime;  // 更新时间
    private Date createtime;  // 创建时间
}
