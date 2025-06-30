package cn.rj.hyhealthbackend.service;

import cn.rj.hyhealthbackend.entity.CompanyPolicyEntity;
import cn.rj.hyhealthbackend.mapper.CompanyPolicyMapper;
import cn.rj.hyhealthbackend.model.CompanyPolicyModel;
import cn.rj.hyhealthbackend.param.CompanyPolicyParam;
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
 * 医药公司政策分页查询功能
 * - 如果页面大小为0，则设为1，通过pageHelper对查询结果进行分页处理，最后将分页查询结果封装成Msg对象并返回
 * - 医药公司政策添加方法，医药公司政策修改方法以及医药公司政策删除方法
 */
@Service
public class CompanyPolicyService {

    @Autowired
    private CompanyPolicyMapper companyPolicyMapper;

    /**
     * 分页、关键字查询医药公司政策信息
     *
     * @param param
     * @return
     */
    public Msg getAllPolicyWithPage(CompanyPolicyParam param) {
        if (param.getSize() == 0) {
            param.setSize(1);
        }
        PageHelper.startPage(param.getPn(), param.getSize());
        List<CompanyPolicyModel> list = companyPolicyMapper.getAllPolicy(param);
        PageInfo<CompanyPolicyModel> info = new PageInfo<>(list, 5);
        return Msg.success().data("policyInfo", info);
    }

    /**
     * 添加医药公司政策
     *
     * @param param
     * @return
     */
    public Msg savePolicy(CompanyPolicyParam param) {
        param.setCreateTime(new DateTime().toDate());
        param.setUpdateTime(new DateTime().toDate());
        CompanyPolicyEntity mpEntity = new CompanyPolicyEntity();
        BeanUtils.copyProperties(param, mpEntity);
        int i = companyPolicyMapper.savePolicy(mpEntity);
        List<CompanyPolicyModel> allPolicy = companyPolicyMapper.getAllPolicy(null);
        mpEntity.setTotal((long) allPolicy.size());
        if (i > 0) {
            Long num = mpEntity.getTotal() % 5 == 0 ? (mpEntity.getTotal() / 5) : (mpEntity.getTotal() / 5) + 1;
            return Msg.success().mess("添加成功").data("numberOfAdd", i).data("pages", num);
        }
        return Msg.fail().mess("添加失败");
    }

    /**
     * 更新医药公司政策
     *
     * @param param
     * @return
     */
    public Msg updatePolicy(Long id, CompanyPolicyParam param) {
        CompanyPolicyEntity entity = new CompanyPolicyEntity();
        BeanUtils.copyProperties(param, entity);
        entity.setUpdateTime(new DateTime().toDate());
        entity.setId(id);
        int i = companyPolicyMapper.updatePolicy(entity);
        if (i > 0) {
            return Msg.success().mess("修改成功").data("updateData", entity);
        }
        return Msg.fail().mess("修改失败");
    }

    /**
     * 根据id删除医药公司政策
     *
     * @param id
     * @return
     */
    public Msg deletePolicy(Long id) {
        int i = companyPolicyMapper.deletePolicy(id);
        if (i > 0) {
            return Msg.success().mess("删除成功").data("numberOfDelete", i);
        }
        return Msg.fail().mess("删除失败");
    }
}
