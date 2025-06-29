package cn.rj.hyhealthbackend.domain.superdomain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author 陈亮
 *
 * 分页查询公共类
 * 包括了总记录数，当前页数，每页大小以及查询关键字
 */
public class SuperDomain implements Serializable {
    private Long total;  // 总记录数
    @JsonIgnore
    private Integer pn = 1;  // 当前页
    @JsonIgnore
    private Integer size = Integer.MAX_VALUE;  // 每页大小
    @JsonIgnore
    private String keyword;  // 查询关键字

    // 默认构造方法
    public SuperDomain() {
    }

    public SuperDomain(Long total, Integer pn, Integer size, String keyword) {
        this.total = total;
        this.pn = pn;
        this.size = size;
        this.keyword = keyword;
    }

    // getter 和 setter 方法
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
