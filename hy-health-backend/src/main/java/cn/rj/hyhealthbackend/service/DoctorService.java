package cn.rj.hyhealthbackend.service;

import cn.rj.hyhealthbackend.model.TreatTypeModel;
import cn.rj.hyhealthbackend.mapper.AccountMapper;
import cn.rj.hyhealthbackend.mapper.DoctorMapper;
import cn.rj.hyhealthbackend.model.DoctorLevelModel;
import cn.rj.hyhealthbackend.model.DoctorModel;
import cn.rj.hyhealthbackend.param.DoctorParam;
import cn.rj.hyhealthbackend.util.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DoctorService {
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private AccountMapper accountMapper;
    /**
     * 关键字、分页查询医师列表
     * @param param
     * @return
     */
    public Msg getDoctorWithPage(DoctorParam param) {
        if (param.getSize() == 0) {
            param.setSize(1);
        }
        PageHelper.startPage(param.getPn(), param.getSize());
        List<DoctorModel> list = doctorMapper.getAllDoctor(param);
        PageInfo<DoctorModel> info = new PageInfo<>(list,5);
        return Msg.success().data("doctorInfo",info);
    }
    /**
     * 获取所有的医生级别信息和诊治类型信息
     * @return
     */
    public Msg getLevelAndType() {
        List<TreatTypeModel> allTreatType = doctorMapper.getAllTreatType();
        List<DoctorLevelModel> allLevel = doctorMapper.getAllLevel();
        return Msg.success().data("allTreatType",allTreatType).data("allLevel",allLevel);
    }
}