package cn.rj.hyhealthbackend.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 陈亮
 * <p>
 * 城市信息实体类
 * - 属性有城市id，城市编号，创建时间以及修改时间
 */
@Getter
@Setter
public class City {

    private Long cityId;  // 城市id
    private Integer cityNumber;  // 城市编号
    private Date createtime;  // 创建时间
    private Date updatetime;  // 修改时间
}
