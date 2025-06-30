package cn.rj.hyhealthbackend.service;

import cn.rj.hyhealthbackend.entity.MedicalPolicyEntity;
import cn.rj.hyhealthbackend.mapper.MedicalPolicyMapper;
import cn.rj.hyhealthbackend.model.MedicalPolicyModel;
import cn.rj.hyhealthbackend.param.MedicalPolicyParam;
import cn.rj.hyhealthbackend.util.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 陈亮
 * <p>
 * MedicalPolicyService
 * - 实现医保政策分页查询功能，通过传入的查询参数调用dao层方法进行查询医保政策，并用pageHelper进行分页处理
 * - 医保政策添加方法、医保政策修改方法以及医保政策删除方法
 */
@Service
public class MedicalPolicyService {

    @Autowired
    private MedicalPolicyMapper medicalPolicyMapper;

    /**
     * 分页、条件查询医保政策信息
     *
     * @param param
     * @return
     */
    public Msg getMedicalPolicyWithPage(MedicalPolicyParam param) {
        PageHelper.startPage(param.getPn(), param.getSize());
        List<MedicalPolicyModel> list = medicalPolicyMapper.getAllPolicy(param);
        PageInfo<MedicalPolicyModel> info = new PageInfo<>(list, 5);
        return Msg.success().data("policyInfo", info);
    }

    /**
     * 新增医保政策信息
     *
     * @param param
     * @return
     */
    public Msg saveMedicalPolicy(MedicalPolicyParam param) {
        param.setCreateTime(new DateTime().toString("yyyy-MM-dd"));
        MedicalPolicyEntity mpEntity = new MedicalPolicyEntity();
        BeanUtils.copyProperties(param, mpEntity);
        int i = medicalPolicyMapper.saveMedicalPolicy(mpEntity);
        List<MedicalPolicyModel> allPolicy = medicalPolicyMapper.getAllPolicy(null);
        mpEntity.setTotal((long) allPolicy.size());
        if (i > 0) {
            Long num = mpEntity.getTotal() % 5 == 0 ? (mpEntity.getTotal() / 5) : (mpEntity.getTotal() / 5) + 1;
            return Msg.success().mess("添加成功").data("numberOfAdd", i).data("pages", num);
        }
        return Msg.fail().mess("添加失败");

    }

    /**
     * 更新医保政策
     *
     * @param param
     * @return
     */
    public Msg updateMedicalPolicy(Long id, MedicalPolicyParam param) {
        MedicalPolicyEntity mpEntity = new MedicalPolicyEntity();
        BeanUtils.copyProperties(param, mpEntity);
        mpEntity.setId(id);
        int i = medicalPolicyMapper.updateMedicalPolicy(mpEntity);
        if (i > 0) {
            return Msg.success().mess("修改成功").data("updateData", mpEntity);
        }
        return Msg.fail().mess("修改失败");
    }

    /**
     * 根据id删除医保信息
     *
     * @param id
     * @return
     */
    public Msg deleteMedicalPolicy(Long id) {
        int i = medicalPolicyMapper.deleteMedicalPolicy(id);
        if (i > 0) {
            return Msg.success().mess("删除成功").data("numberOfMPolicyDelete", i);
        }
        return Msg.fail().mess("删除失败");
    }
}
