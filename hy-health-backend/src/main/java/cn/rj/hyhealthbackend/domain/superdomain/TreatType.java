package cn.rj.hyhealthbackend.domain.superdomain;


/**
 * @author 李柯嘉
 *
 * 诊治类别实体类
 * 诊治类型id以及诊治类型名称name
 */
public class TreatType extends SuperDomain{
    private Long id;
    private String name;

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
