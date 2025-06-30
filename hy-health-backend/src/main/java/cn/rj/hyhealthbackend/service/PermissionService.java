package cn.rj.hyhealthbackend.service;

import cn.rj.hyhealthbackend.domain.Permission;
import cn.rj.hyhealthbackend.mapper.PermissionMapper;
import cn.rj.hyhealthbackend.model.PermissionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈亮
 * <p>
 * 用户菜单管理服务类
 * - 实现通过用户权限获取用户列表方法和通过父节点添加子节点方法
 */
@Service
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 获取所有权限
     *
     * @param roleName
     * @return
     */
    public List<PermissionModel> getAllPermission(String roleName) {
        String finalRoleName = "ROLE_" + roleName;
        List<PermissionModel> allPermission = permissionMapper.getPermission(finalRoleName.trim());
        PermissionModel fatherPermisson = new PermissionModel();
        List<PermissionModel> finalPermisson = new ArrayList<>();
        for (PermissionModel per : allPermission) {
            if (per.getPid() == 0) {
                finalPermisson.add(selectChildren(per, allPermission, finalRoleName));
            }
        }
        return finalPermisson;
    }

    /**
     * 为父节点添加children
     *
     * @param father
     * @param allPermission
     * @return
     */
    public PermissionModel selectChildren(PermissionModel father, List<PermissionModel> allPermission, String finalRoleName) {
        List<Permission> list = new ArrayList<>();
        allPermission.forEach(item -> {
            if (!finalRoleName.equals("ROLE_1")) {
                String title = item.getMeta().getTitle().replace("管理", "查询");
                item.getMeta().setTitle(title);
            }
            if (father.getId() == item.getPid()) {
                father.setChildren(list);
                father.getChildren().add(selectChildren(item, allPermission, finalRoleName));
            }
        });
        return father;
    }
}
