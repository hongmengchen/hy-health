package cn.rj.hyhealthbackend.service;

import cn.rj.hyhealthbackend.domain.Permission;
import cn.rj.hyhealthbackend.mapper.PermissionMapper;
import cn.rj.hyhealthbackend.model.PermissionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    // 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(PermissionService.class);

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 获取所有的权限菜单列表
     *
     * @return
     */
    public List<PermissionModel> getAllPermission(String roleName) {
        String finalRoleName = "ROLE_" + roleName;
        List<PermissionModel> allPermission = permissionMapper.getPermission(finalRoleName.trim());

        logger.info("查询到的权限菜单数量: {}", allPermission.size());

        List<PermissionModel> finalPermisson = new ArrayList<>();
        for (PermissionModel per : allPermission) {
            if (per.getPid() == 0) {
                logger.info("发现顶级菜单: {}", per.getName());
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
