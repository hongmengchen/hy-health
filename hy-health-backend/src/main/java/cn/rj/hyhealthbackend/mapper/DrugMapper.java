package cn.rj.hyhealthbackend.mapper;

import cn.rj.hyhealthbackend.model.DrugModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 陈亮
 * <p>
 * 药品信息数据访问接口
 * - 药品信息查询方法，通过传入参数进行模糊查询
 **/
@Component
public interface DrugMapper {
    // 查询所有药品同时,封装药品销售地点
    List<DrugModel> getAllDrug(String name);
}
