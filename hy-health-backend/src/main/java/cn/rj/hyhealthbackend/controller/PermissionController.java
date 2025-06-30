package cn.rj.hyhealthbackend.controller;

import cn.rj.hyhealthbackend.service.PermissionService;
import cn.rj.hyhealthbackend.util.Msg;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈亮
 * <p>
 * 用户菜单控制器
 * - 实现通过获取前端传入用户权限并通过service层方法获取用户菜单管理列表，将列表数据封装到Msg对象中并返回给前端
 */
@Api(tags = "权限菜单控制器")
@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping(value = "")
    public Msg getAllPermission(String roleName) {
        return Msg.success().data("permissions", permissionService.getAllPermission(roleName));
    }
}
