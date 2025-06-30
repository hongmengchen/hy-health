package cn.rj.hyhealthbackend.entity;

import cn.rj.hyhealthbackend.domain.DrugCompany;

/**
 * @author 陈亮
 * <p>
 * 医药公司数据模型
 * 用于对数据库进行操作
 */
public class DrugCompanyEntity extends DrugCompany {

    private Long total;  // 插入存储返回插入后的总记录数

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}

