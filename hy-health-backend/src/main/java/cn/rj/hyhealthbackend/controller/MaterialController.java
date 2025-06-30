package cn.rj.hyhealthbackend.controller;

import cn.rj.hyhealthbackend.param.MaterialParam;
import cn.rj.hyhealthbackend.service.MaterialService;
import cn.rj.hyhealthbackend.util.Msg;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@Api(tags = "必备材料控制器")
@RestController

@RequestMapping("/api/materials")
@CrossOrigin
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    /**
     * 分页、关键字查询必备材料
     * @param param
     * @return
     */
    @GetMapping(value = "")
    public Msg getPolicyWithPage(MaterialParam param) {
        Msg msg = materialService.getAllMaterialWithPage(param);
        return msg;
    }

    /**
     * 添加必备材料
     * @param param
     * @return
     */
    @RolesAllowed({"1"})
    @PostMapping(value = "")
    public Msg saveMedicalPolicy(@RequestBody MaterialParam param) {
        Msg msg = materialService.saveMaterial(param);
        return msg;
    }

    /**
     * 更新必备材料
     * @param param
     * @return
     */
    @RolesAllowed({"1"})
    @PutMapping(value = "/{id}")
    public Msg updateMedicalPolicy(@PathVariable("id") Integer id, @RequestBody MaterialParam param) {
        if (!StringUtils.hasLength(param.getTitle())) {
            return Msg.fail().mess("标题不能为空");
        }
        if (!StringUtils.hasLength(param.getMessage())) {
            return Msg.fail().mess("内容不能为空");
        }
        return materialService.updateMaterial(id, param);
    }

    /**
     * 根据id删除必备材料
     * @param id
     * @return
     */
    @RolesAllowed({"1"})
    @DeleteMapping("/{id}")
    public Msg deletePolicy(@PathVariable("id") Long id) {
        return materialService.deleteMaterial(id);
    }
}
