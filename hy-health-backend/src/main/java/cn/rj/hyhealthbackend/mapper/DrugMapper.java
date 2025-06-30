package cn.rj.hyhealthbackend.mapper;

import cn.rj.hyhealthbackend.entity.DrugEntity;
import cn.rj.hyhealthbackend.model.DrugModel;
import cn.rj.hyhealthbackend.param.DrugParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 陈亮
 * <p>
 * 药品信息数据访问接口
 * - 药品信息查询方法，通过传入参数进行模糊查询
 * - 药品信息添加方法，药品信息修改方法，药品信息删除方法以及删除药品信息与药店关联关系方法，其中deleteSaleByDrugId方法是根据药品id删除数据库中的药品药店关联关系表
 **/
@Component
public interface DrugMapper {
    // 查询所有药品同时,封装药品销售地点
    List<DrugModel> getAllDrug(String name);

    /*新增药品信息*/
    int saveDrug(DrugParam drugParam);

    /*根据id更新药店数据*/
    int updateDrugById(DrugEntity sale);

    /*插入该药店的售卖地点*/
    int insertSalePlace(Long drugId, Long[] saleIds);

    /*根据药品id删除对应的售卖地点*/
    int deleteSaleByDrugId(Long drugId);

    int deleteDrugById(Long drugId);
}
