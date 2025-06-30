package cn.rj.hyhealthbackend.service;

import cn.rj.hyhealthbackend.mapper.DrugMapper;
import cn.rj.hyhealthbackend.model.DrugModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 陈亮
 * <p>
 * 药品信息服务类
 * - 药品信息分页查询方法
 */
@Service
@Transactional
public class DrugService {

    @Autowired
    private DrugMapper drugMapper;

    /**
     * 获取所有药品信息并分页，name不为空则模糊查询
     *
     * @param pn
     * @param size
     * @param name
     */
    public PageInfo<DrugModel> getDrugWithPage(int pn, int size, String name) {
        PageHelper.startPage(pn, size);
        List<DrugModel> list = drugMapper.getAllDrug(name);
        //list.forEach();
        PageInfo<DrugModel> info = new PageInfo<>(list, 5);
        return info;
    }
}