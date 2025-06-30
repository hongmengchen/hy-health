package cn.rj.hyhealthbackend.service;

import cn.rj.hyhealthbackend.mapper.CompanyPolicyMapper;
import cn.rj.hyhealthbackend.model.CompanyPolicyModel;
import cn.rj.hyhealthbackend.param.CompanyPolicyParam;
import cn.rj.hyhealthbackend.util.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 陈亮
 * <p>
 * 医药公司政策分页查询功能
 * - 如果页面大小为0，则设为1，通过pageHelper对查询结果进行分页处理，最后将分页查询结果封装成Msg对象并返回
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
}
