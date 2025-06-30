package cn.rj.hyhealthbackend.model;

import cn.rj.hyhealthbackend.domain.CompanyPolicy;

/**
 * @author 陈亮
 * <p>
 * 医药公司政策业务模型
 * - 用于对前端进行数据交互，新增属性DrugCompanyModel存储医药公司信息
 */
public class CompanyPolicyModel extends CompanyPolicy {

    private DrugCompanyModel drugCompanyModel;

    public DrugCompanyModel getDrugCompanyModel() {
        return drugCompanyModel;
    }

    public void setDrugCompanyModel(DrugCompanyModel drugCompanyModel) {
        this.drugCompanyModel = drugCompanyModel;
    }
}
