package cn.rj.hyhealthbackend.controller;


import cn.rj.hyhealthbackend.param.DoctorParam;
import cn.rj.hyhealthbackend.service.DoctorService;
import cn.rj.hyhealthbackend.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import javax.annotation.security.RolesAllowed;

@Api(tags = "医师控制器类")
@RestController
@RequestMapping("/api/doctors")
@CrossOrigin
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    /**
     * 新增医师信息
     * @param param
     * @return
     */
    @RolesAllowed({"1","2"})
    @PostMapping(value = "")
    public Msg saveDoctor(@RequestBody @Validated DoctorParam param) {
        return doctorService.saveDoctor(param);
    }

    /**
     * 获取所有的医生级别信息和诊治类型信息
     * @return
     */
    @RolesAllowed({"1","2"})
    @GetMapping("/info")
    public Msg getLevelAndType() {
        return doctorService.getLevelAndType();
    }

    /**
     * 修改医师信息
     * @param param
     * @return
     */
    @RolesAllowed({"1"})
    @PutMapping("/{id}")
    public Msg updateDoctor(@PathVariable("id") Long id, @RequestBody DoctorParam param) {
        return doctorService.updateDoctor(id, param);
    }

    /**
     * 根据id删除医师信息并且删除其账户
     * @param id
     * @return
     */
    @RolesAllowed({"1"})
    @DeleteMapping("{id}")
    public Msg deleteDoctor(@PathVariable("id") Long id) {

        return doctorService.deleteDoctorById(id);
    }

    /**
     * 重置医师密码
     * @param id
     * @return
     */
    @RolesAllowed({"1"})
    @PutMapping("/reset/{id}")
    public Msg resetPwd(@PathVariable Long id) {
        return doctorService.resetPwd(id);
    }
    /**
     * 关键字、分页查询医师列表
     * @param param
     * @return
     */
    @RolesAllowed({"1","2"})
    @GetMapping(value = "")
    public Msg getDoctorWithPage(DoctorParam param) {
        System.out.println(param.toString());
        return doctorService.getDoctorWithPage(param);
    }
}