package cn.rj.hyhealthbackend.domain;

import cn.rj.hyhealthbackend.domain.superdomain.SuperDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 陈亮
 * <p>
 * 菜单管理实体类
 * - 继承SuperDomain，其中属性有菜单id，菜单父id，菜单路径，菜单名，菜单组件，菜单级别和子菜单
 */
@Getter
@Setter
public class Permission extends SuperDomain {
    @JsonIgnore
    private Integer id;//菜单id
    @JsonIgnore
    private Integer pid;//菜单父id
    private String path;//菜单路径
    private String name;//菜单name
    private String component;//菜单组件
    @JsonIgnore
    private Integer level;//菜单级别
    private List<Permission> children;//子菜单
}
