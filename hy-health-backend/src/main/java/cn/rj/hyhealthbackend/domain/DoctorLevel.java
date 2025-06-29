package cn.rj.hyhealthbackend.domain;

import cn.rj.hyhealthbackend.domain.superdomain.SuperDomain;

/**
 * @author 梁旭
 *
 * 医师级别实体类
 */
public class DoctorLevel extends SuperDomain {
    private Long id;//级别id
    private String name;//级别名称

    // getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
