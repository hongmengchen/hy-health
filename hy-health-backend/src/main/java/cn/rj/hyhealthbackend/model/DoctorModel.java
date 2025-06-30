package cn.rj.hyhealthbackend.model;

import cn.rj.hyhealthbackend.domain.Doctor;
import lombok.Data;

/**
 * @author 李柯嘉
 *
 * 医师数据模型类
 * 用于返回前端数据，新加入诊治级别和医师级别属性
 */
@Data
public class DoctorModel extends Doctor {
    private String treatType;//诊治类型
    private String doctorLevel;//医师级别

}
