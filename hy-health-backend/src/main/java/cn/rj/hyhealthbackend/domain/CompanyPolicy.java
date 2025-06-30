package cn.rj.hyhealthbackend.domain;

import cn.rj.hyhealthbackend.domain.superdomain.SuperDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 陈亮
 * <p>
 * 医药公司政策实体类
 * - 属性包括医药公司政策id，医药公司政策标题，医药公司政策内容，医药公司id，创建时间以及发布时间
 */
@Getter
@Setter
public class CompanyPolicy extends SuperDomain {
    private Long id;  // 医药公司政策id
    private String title;  // 医药公司政策标题
    private String message;  // 医药公司政策内容
    private Long companyId;  // 医药公司id
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;  // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;  // 发布时间
}
