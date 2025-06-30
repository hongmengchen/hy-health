package cn.rj.hyhealthbackend.model;

import cn.rj.hyhealthbackend.domain.Permission;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 陈亮
 * <p>
 * 菜单管理业务模型
 * - 继承Permission，并添加菜单标题属性
 */
@Getter
@Setter
public class PermissionModel extends Permission {
    private MetaModel meta;  // 存放meta封装的各个属性，icon、title等
}
