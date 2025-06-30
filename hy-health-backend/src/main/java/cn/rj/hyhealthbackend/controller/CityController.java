package cn.rj.hyhealthbackend.controller;

import cn.rj.hyhealthbackend.model.CityModel;
import cn.rj.hyhealthbackend.service.CityService;
import cn.rj.hyhealthbackend.util.Msg;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * @author 陈亮
 * <p>
 * CityController
 * - 实现根据id查询城市和分页查询城市接口。其中分页查询城市接收前端传入的分页参数和查询关键字，并通过service层方法进行查询，如果查询结果为null，则返回失败信息，否则将查询结果加入到成功信息中并返回给前端
 * - 实现城市信息添加接口以及城市信息删除接口
 */
@Api(tags = "城市信息控制器")
@RestController
@RequestMapping("/api/citys")
@CrossOrigin
public class CityController {
    @Autowired
    private CityService cityService;

    /**
     * 分页查询城市接口
     *
     * @param pn   当前页码
     * @param size 每页显示数量
     * @param name 查询关键字
     * @return
     */
    @GetMapping(value = {"/{pn}/{size}", ""})
    public Msg getCityWithPage(@PathVariable(value = "pn", required = false) Integer pn,
                               @PathVariable(value = "size", required = false) Integer size,
                               @RequestParam(required = false) String name) {
        PageInfo<CityModel> info = cityService.getCityWithPage(pn, size, name);
        if (info != null) {
            return Msg.success().data("cityPageInfo", info);
        }
        return Msg.fail();
    }

    /**
     * 根据id查询一个城市信息
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Msg getCityById(@PathVariable("id") Integer id) {
        Msg msg = cityService.getCityById(id);
        return msg;
    }

    /**
     * 新增一个城市信息
     *
     * @return
     */
    @RolesAllowed({"1"})
    @PostMapping(value = "")
    public Msg saveCity(Integer cityNumber) {
        if (cityService.checkCityByName(cityNumber) > 0) {
            return Msg.fail().mess("城市已经存在").code(10004);
        }
        return cityService.saveCity(cityNumber);
    }

    /**
     * 根据id删除城市
     *
     * @param id
     * @return
     */
    @RolesAllowed({"1"})
    @DeleteMapping("{id}")
    public Msg deleteSaleById(@PathVariable("id") Integer id) {
        Msg msg = cityService.deleteCityById(id);
        return msg;
    }
}
