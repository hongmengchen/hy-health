package cn.rj.hyhealthbackend.controller;

import cn.rj.hyhealthbackend.model.DrugModel;
import cn.rj.hyhealthbackend.service.DrugService;
import cn.rj.hyhealthbackend.util.Msg;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 陈亮
 * <p>
 * 药品信息控制器
 * - 实现药品信息分页查询接口
 */
@Api(tags = "药品控制器类")
@RestController
@RequestMapping("/api/drugs")
@CrossOrigin
public class DrugController {

    @Autowired
    private DrugService drugService;

    /**
     * 药品信息的分页查询,name不为空则模糊查询
     *
     * @param pn
     * @param size
     * @param name
     * @return
     */
    @GetMapping("/{pn}/{size}")
    public Msg getDrugWithPage(@PathVariable("pn") int pn, @PathVariable("size") int size, @RequestParam(required = false) String name) {
        PageInfo<DrugModel> info = drugService.getDrugWithPage(pn, size, name);
        if (info != null) {
            return Msg.success().data("drugPageInfo", info);
        }
        return Msg.fail();
    }
}