package cn.rj.hyhealthbackend.model;

import cn.rj.hyhealthbackend.domain.City;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 陈亮
 * <p>
 * 城市信息业务模型
 * - 继承City，用于返回前端数据，新增所在省和城市名
 */
@Getter
@Setter
public class CityModel extends City {
    private String province;  // 省份
    private String city;  // 市
}
