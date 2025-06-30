package cn.rj.hyhealthbackend.mapper;

import cn.rj.hyhealthbackend.entity.MaterialEntity;
import cn.rj.hyhealthbackend.model.MaterialModel;
import cn.rj.hyhealthbackend.param.MaterialParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MaterialMapper {

    /*查询所有的材料*/
    List<MaterialModel> getAllMaterial(MaterialParam param);

    /*新增材料*/
    int saveMaterial(MaterialEntity entity);

    /*更新材料*/
    int updateMaterial(MaterialEntity entity);

    /*删除材料*/
    int deleteMaterial(Long id);
}
