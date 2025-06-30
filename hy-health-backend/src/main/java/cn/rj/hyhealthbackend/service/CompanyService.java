package cn.rj.hyhealthbackend.service;

import cn.rj.hyhealthbackend.domain.DrugCompany;
import cn.rj.hyhealthbackend.entity.DrugCompanyEntity;
import cn.rj.hyhealthbackend.mapper.CompanyMapper;
import cn.rj.hyhealthbackend.mapper.CompanyPolicyMapper;
import cn.rj.hyhealthbackend.util.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

/**
 * @author 陈亮
 * <p>
 * 医药公司服务层
 * - 添加根据公司名模糊分页查询以及根据id查询医药公司信息功能，在分页查询时，如果传入的页码和页面大小为null或者0，则设置默认页码和页面大小，然后通过pageHelper进行分页，通过id查询时，如果没有查询到对应id的信息，则返回信息“没有找到”，否则返回查询到的公司信息
 * - 医药公司添加方法，医药公司修改方法以及医药公司删除方法
 * - 医药公司删除方法，在删除医药公司的同时删除对应的医保政策
 */
@Service
public class CompanyService {
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private CompanyPolicyMapper companyPolicyMapper;

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

    /**
     * 添加一个医药公司
     *
     * @param company
     * @return
     */
    public Msg saveCompany(DrugCompany company) {
        Date d = new Date();
        company.setCreatetime(d);
        company.setUpdatetime(d);
        DrugCompanyEntity dce = new DrugCompanyEntity();
        BeanUtils.copyProperties(company, dce);//对象拷贝
        int i = companyMapper.saveCompany(dce);
        if (i > 0) {
            Long num = dce.getTotal() % 5 == 0 ? (dce.getTotal() / 5) : (dce.getTotal() / 5) + 1;
            return Msg.success().data("pages", num).mess("添加成功");
        }
        return Msg.fail().mess("添加失败");
    }

    /**
     * 根据id更新医药公司信息
     *
     * @param company
     * @return
     */
    public Msg updateCompanyById(Long id, @RequestBody DrugCompany company) {
        company.setUpdatetime(new Date());
        company.setCompanyId(id);
        int i = companyMapper.updateCompanyById(company);
        if (i > 0) {
            return Msg.success().mess("修改成功");
        }
        return Msg.fail().mess("修改失败");
    }

    /**
     * 根据id删除医药公司信息
     *
     * @param id
     * @return
     */
    public Msg deleteCompanyById(Integer id) {
        int i = companyMapper.deleteCompanyById(id);
        companyPolicyMapper.deletePolicyByCompany(id);
        if (i > 0) {
            return Msg.success().mess("删除成功");
        } else {
            return Msg.fail().mess("删除失败");
        }
    }
}
