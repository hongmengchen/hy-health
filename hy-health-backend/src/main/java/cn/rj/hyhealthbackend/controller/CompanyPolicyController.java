package cn.rj.hyhealthbackend.controller;

import cn.rj.hyhealthbackend.param.CompanyPolicyParam;
import cn.rj.hyhealthbackend.service.CompanyPolicyService;
import cn.rj.hyhealthbackend.util.Msg;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈亮
 * <p>
 * CompanyPolicyController
 * - 实现分页查询医药公司信息接口，通过前端查询参数查询并返回前端分页查询数据
 */
@Api(tags = "医药公司政策控制器类")
@RestController
@RequestMapping("/api/company_policys")
@CrossOrigin
public class CompanyPolicyController {

    @Autowired
    private CompanyPolicyService companyPolicyService;

    /**
     * 分页、关键字查询医药公司政策信息
     *
     * @param param
     * @return
     */
    @GetMapping(value = "")
    public Msg getPolicyWithPage(CompanyPolicyParam param) {
        Msg msg = companyPolicyService.getAllPolicyWithPage(param);
        return msg;
    }
}
