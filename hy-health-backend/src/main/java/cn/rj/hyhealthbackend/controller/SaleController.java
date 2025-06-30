package cn.rj.hyhealthbackend.controller;

import cn.rj.hyhealthbackend.domain.Sale;
import cn.rj.hyhealthbackend.service.SaleService;
import cn.rj.hyhealthbackend.util.Msg;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李柯嘉
 * <p>
 * SaleController
 * - 根据前端传入参数实现分页查询以及根据前端传入id查询对应的销售地点接口
 */
@Api(tags = "药店信息控制器")
@RestController
@RequestMapping("/api/sales")
//@CrossOrigin
public class SaleController {

    @Autowired
    private SaleService saleService;

    /**
     * 药店信息的分页查询,name不为空则模糊查询,当路径为""时，查询不分页
     * @param pn
     * @param size
     * @param name
     * @return
     */
    @GetMapping(value = {"/{pn}/{size}",""})
    public Msg getSaleWithPage(@PathVariable(value = "pn",required = false) Integer pn,
                               @PathVariable(value = "size",required = false) Integer size,
                               @RequestParam(required = false) String name) {
        PageInfo<Sale> info = saleService.getSaleWithPage(pn, size, name);
        if (info != null) {
            return Msg.success().data("salePageInfo",info);
        }
        return Msg.fail();
    }


    /**
     * 根据id查询一个药店
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Msg getSaleById(@PathVariable("id") Integer id) {
        Msg msg = saleService.getSaleById(id);
        return msg;
    }

}