package cn.rj.hyhealthbackend.controller;

import cn.rj.hyhealthbackend.param.MaterialParam;
import cn.rj.hyhealthbackend.service.MaterialService;
import cn.rj.hyhealthbackend.util.Msg;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
