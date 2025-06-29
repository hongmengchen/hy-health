package cn.rj.hyhealthbackend.domain.superdomain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author 陈亮
 * <p>
 * 分页查询公共类
 * 包括了总记录数，当前页数，每页大小以及查询关键字
 */
@Data
public class SuperDomain implements Serializable {
    private Long total;  // 总记录数
    @JsonIgnore
    private Integer pn = 1;  // 当前页
    @JsonIgnore
    private Integer size = Integer.MAX_VALUE;  // 每页大小
    @JsonIgnore
    private String keyword;  // 查询关键字
}
