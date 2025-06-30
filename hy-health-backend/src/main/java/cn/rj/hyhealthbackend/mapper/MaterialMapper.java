package cn.rj.hyhealthbackend.mapper;

import cn.rj.hyhealthbackend.model.MaterialModel;
import cn.rj.hyhealthbackend.param.MaterialParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MaterialMapper {

    /*查询所有的材料*/
    List<MaterialModel> getAllMaterial(MaterialParam param);


}
