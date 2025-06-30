package cn.rj.hyhealthbackend.domain;

import cn.rj.hyhealthbackend.domain.superdomain.SuperDomain;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

/**
 * @author 陈亮
 * <p>
 * 医保政策实体类
 * - 继承SuperDomain，医保政策属性有政策id，政策标题，政策内容，对应城市，创建时间以及更新发布时间
 */
@Getter
@Setter
public class MedicalPolicy extends SuperDomain {

    @Pattern(regexp = "(^[0-9]*)", message = "编号只能为数字")
    private Long id;  // 政策id
    private String title;  // 政策标题
    private String message;  // 政策消息
    private Long cityId;  // 政策的对应城市
    private String createTime;  // 创建时间
    private String updateTime;  // 更新时间兼发布时间
}
