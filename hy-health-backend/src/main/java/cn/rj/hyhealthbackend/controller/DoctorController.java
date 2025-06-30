package cn.rj.hyhealthbackend.controller;


import cn.rj.hyhealthbackend.param.DoctorParam;
import cn.rj.hyhealthbackend.service.DoctorService;
import cn.rj.hyhealthbackend.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    /**
     * 获取所有的医生级别信息和诊治类型信息
     * @return
     */
    @RolesAllowed({"1","2"})
    @GetMapping("/info")
    public Msg getLevelAndType() {
        return doctorService.getLevelAndType();
    }
}