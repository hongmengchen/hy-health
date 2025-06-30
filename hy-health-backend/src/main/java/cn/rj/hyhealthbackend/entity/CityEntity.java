package cn.rj.hyhealthbackend.entity;

import cn.rj.hyhealthbackend.domain.City;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 陈亮
 * <p>
 * 城市信息数据模型
 * - 继承City，用于对数据库进行数据交互
 */
@Getter
@Setter
public class CityEntity extends City {

    private Long total;
}
