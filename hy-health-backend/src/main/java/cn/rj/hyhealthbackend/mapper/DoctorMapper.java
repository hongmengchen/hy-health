package cn.rj.hyhealthbackend.mapper;

import cn.rj.hyhealthbackend.model.TreatTypeModel;
import cn.rj.hyhealthbackend.model.DoctorLevelModel;
import cn.rj.hyhealthbackend.model.DoctorModel;
import cn.rj.hyhealthbackend.param.DoctorParam;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author 李柯嘉
 *
 * DoctorMapper接口
 * 并声明查询医师信息，医师级别信息以及诊治类型信息的方法
 * 获取所有医师信息方法根据传入的查询参数查询对应的医师信息。
 */
@Component
public interface DoctorMapper {
    /*获取所有的医师信息*/
    List<DoctorModel> getAllDoctor(DoctorParam param);
    /*获取所有的医师级别*/
    List<DoctorLevelModel> getAllLevel();
    /*获取所有的诊治类型*/
    List<TreatTypeModel> getAllTreatType();
}
