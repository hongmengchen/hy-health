package cn.rj.hyhealthbackend.model;

import cn.rj.hyhealthbackend.domain.Doctor;

/**
 * @author 李柯嘉
 *
 * 医生数据模型类
 * 用于返回前端数据
 * 新加入诊治级别和医师级别属性
 */
public class DoctorModel extends Doctor {
    private String treatType;//诊治类型
    private String doctorLevel;//医师级别
    //省略get/set方法

    public String getTreatType() {
        return treatType;
    }

    public void setTreatType(String treatType) {
        this.treatType = treatType;
    }

    public String getDoctorLevel() {
        return doctorLevel;
    }

    public void setDoctorLevel(String doctorLevel) {
        this.doctorLevel = doctorLevel;
    }
}
