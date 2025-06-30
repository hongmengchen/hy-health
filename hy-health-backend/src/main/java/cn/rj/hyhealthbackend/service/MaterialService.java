package cn.rj.hyhealthbackend.service;

import cn.rj.hyhealthbackend.entity.MaterialEntity;
import cn.rj.hyhealthbackend.mapper.MaterialMapper;
import cn.rj.hyhealthbackend.model.MaterialModel;
import cn.rj.hyhealthbackend.param.MaterialParam;
import cn.rj.hyhealthbackend.util.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    /**
     * 分页、关键字查询必备材料信息
     * @param param
     * @return
     */
    public Msg getAllMaterialWithPage(MaterialParam param) {
        if (param.getSize() == 0) {
            param.setSize(1);
        }
        PageHelper.startPage(param.getPn(), param.getSize());
        List<MaterialModel> list = materialMapper.getAllMaterial(param);
        PageInfo<MaterialModel> info = new PageInfo<>(list,5);
        return Msg.success().data("materialInfo",info);
    }

    /**
     * 添加必备材料
     * @param param
     * @return
     */
    public Msg saveMaterial(MaterialParam param) {
        param.setCreateTime(new DateTime().toDate());
        param.setUpdateTime(new DateTime().toDate());
        MaterialEntity mEntity = new MaterialEntity();
        BeanUtils.copyProperties(param,mEntity);
        int i = materialMapper.saveMaterial(mEntity);
        List<MaterialModel> allMaterial = materialMapper.getAllMaterial(null);
        mEntity.setTotal((long) allMaterial.size());
        if (i > 0) {
            Long num = mEntity.getTotal() % 5 == 0 ? (mEntity.getTotal() / 5) : (mEntity.getTotal() / 5)+1;
            return Msg.success().mess("添加成功").data("numberOfAdd",i).data("pages",num);
        }
        return Msg.fail().mess("添加失败");
    }

    /**
     * 更新必备材料
     * @param param
     * @return
     */
    public Msg updateMaterial(Integer id, MaterialParam param) {
        MaterialEntity entity = new MaterialEntity();
        BeanUtils.copyProperties(param,entity);
        entity.setUpdateTime(new DateTime().toDate());
        entity.setId(id);
        int i = materialMapper.updateMaterial(entity);
        if (i > 0) {
            return Msg.success().mess("修改成功").data("updateData",entity);
        }
        return Msg.fail().mess("修改失败");
    }

    /**
     * 根据id删除必备材料
     * @param id
     * @return
     */
    public Msg deleteMaterial(Long id) {
        int i = materialMapper.deleteMaterial(id);
        if (i > 0) {
            return Msg.success().mess("删除成功").data("numberOfDelete",i);
        }
        return Msg.fail().mess("删除失败");
    }
}
