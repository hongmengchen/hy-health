package cn.rj.hyhealthbackend.mapper;

import cn.rj.hyhealthbackend.model.PermissionModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 陈亮
 *
 * 菜单管理数据访问接口
 * - 通过用户权限获取用户菜单列表，通过传入用户权限名返回用户菜单权限列表
 */
@Component
public interface PermissionMapper {
    List<PermissionModel> getPermission(String roleName);
}
