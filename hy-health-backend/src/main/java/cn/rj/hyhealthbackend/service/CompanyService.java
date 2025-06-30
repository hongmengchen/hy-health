package cn.rj.hyhealthbackend.service;

import cn.rj.hyhealthbackend.domain.DrugCompany;
import cn.rj.hyhealthbackend.mapper.CompanyMapper;
import cn.rj.hyhealthbackend.util.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 陈亮
 * <p>
 * 医药公司服务层
 * 添加根据公司名模糊分页查询以及根据id查询医药公司信息功能，在分页查询时，如果传入的页码和页面大小为null或者0，则设置默认页码和页面大小，然后通过pageHelper进行分页，通过id查询时，如果没有查询到对应id的信息，则返回信息“没有找到”，否则返回查询到的公司信息
 */
@Service
public class CompanyService {
    @Autowired
    private CompanyMapper companyMapper;
    /*@Autowired
    private CompanyPolicyMapper companyPolicyMapper;*/

    /**
     * 获取所有医药公司信息并分页，name不为空则模糊查询
     *
     * @param pn
     * @param size
     * @param name
     */
    public PageInfo<DrugCompany> getCompanyWithPage(Integer pn, Integer size, String name) {
        if (pn == null && size == null) {
            pn = 1;
            size = Integer.MAX_VALUE;
        }
        if (pn == null) {
            pn = 1;
        }
        if (size == null) {
            size = Integer.MAX_VALUE;
        }
        if (size == 0) {
            size = 1;
        }
        PageHelper.startPage(pn, size);
        List<DrugCompany> list = companyMapper.getAllCompany(name);
        PageInfo<DrugCompany> info = new PageInfo<>(list, 5);
        return info;
    }

    /**
     * 根据id查找一个医药公司
     *
     * @param id
     * @return
     */
    public Msg getCompanyById(Integer id) {
        DrugCompany company = companyMapper.getCompanyById(id);

        if (company == null) {
            return Msg.fail().mess("没有找到");
        }
        return Msg.success().data("company", company);
    }
}
