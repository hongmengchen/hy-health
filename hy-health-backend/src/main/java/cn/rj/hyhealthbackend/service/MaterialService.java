package cn.rj.hyhealthbackend.service;

import cn.rj.hyhealthbackend.mapper.MaterialMapper;
import cn.rj.hyhealthbackend.model.MaterialModel;
import cn.rj.hyhealthbackend.param.MaterialParam;
import cn.rj.hyhealthbackend.util.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

}
