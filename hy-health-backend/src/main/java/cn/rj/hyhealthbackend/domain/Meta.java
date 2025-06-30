package cn.rj.hyhealthbackend.domain;

import cn.rj.hyhealthbackend.domain.superdomain.SuperDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 陈亮
 * <p>
 * 菜单标题实体类
 * - 继承SuperDomain，其中属性有菜单标题title
 */
@Getter
@Setter
public class Meta extends SuperDomain {
    private String title;  // 菜单title
}
