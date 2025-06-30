package cn.rj.hyhealthbackend.mapper;

import cn.rj.hyhealthbackend.domain.City;
import cn.rj.hyhealthbackend.entity.CityEntity;
import cn.rj.hyhealthbackend.model.CityModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 陈亮
 * <p>
 * CityMapper
 * - 通过关键字获取所有城市信息以及根据id查询城市方法
 * - 城市信息添加方法、城市信息删除方法以及查询城市是否存在方法
 */
@Component
public interface CityMapper {

    // 获取所有城市信息,name不为空则模糊查询
    List<CityModel> getAllCity(String name);

    // 根据id查找一个城市信息
    City getCityById(Integer id);

    // 添加一个城市信息
    int saveCity(CityEntity city);

    // 根据id删除城市信息
    int deleteCityById(Integer id);

    // 查询城市是否存在
    int checkCityByName(Integer number);

}
