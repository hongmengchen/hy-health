package cn.rj.hyhealthbackend.param;

import cn.rj.hyhealthbackend.domain.Doctor;
import lombok.Data;

/**
 * @author 李柯嘉
 *
 * 医师查询参数类
 * 用于接收前端传入的查询参数，新增医师密码属性
 */
@Data
public class DoctorParam extends Doctor {
    private String pwd;//新增医师的密码
}
