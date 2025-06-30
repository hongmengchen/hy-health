package cn.rj.hyhealthbackend.controller;

import cn.rj.hyhealthbackend.domain.DrugCompany;
import cn.rj.hyhealthbackend.service.CompanyService;
import cn.rj.hyhealthbackend.util.Msg;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 陈亮
 * <p>
 * 医药公司控制器层
 * 添加分页查询医药公司控制器以及根据id查询医药公司控制器，分页查询通过请求路径获得查询页码以及页面大小，调用service层方法查询分页，如果查询结果不为空则返回封装后的数据，为空则返回失败信息
 */

@Api(tags = "医药公司信息控制器")
@RestController

@RequestMapping("/api/companys")
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 医药公司信息的分页查询,name不为空则模糊查询
     *
     * @param pn
     * @param size
     * @param name
     * @return
     */
    @GetMapping(value = {"/{pn}/{size}", ""})
    public Msg getCompanyWithPage(@PathVariable(value = "pn", required = false) Integer pn,
                                  @PathVariable(value = "size", required = false) Integer size,
                                  @RequestParam(required = false) String name) {
        PageInfo<DrugCompany> info = companyService.getCompanyWithPage(pn, size, name);
        if (info != null) {
            return Msg.success().data("pageInfo", info);
        }
        return Msg.fail();
    }

    /**
     * 根据id查询一个医药公司信息
     *
     * @param id
     * @return 封装后的数据
     */
    @GetMapping("{id}")
    public Msg getCompanyById(@PathVariable("id") Integer id) {
        Msg msg = companyService.getCompanyById(id);
        return msg;
    }

}
