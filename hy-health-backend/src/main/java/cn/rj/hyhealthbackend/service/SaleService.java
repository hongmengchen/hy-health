package cn.rj.hyhealthbackend.service;

import cn.rj.hyhealthbackend.domain.Sale;
import cn.rj.hyhealthbackend.mapper.SaleMapper;
import cn.rj.hyhealthbackend.util.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李柯嘉
 * <p>
 * SaleService
 * - 实现分页查询方法和根据id查询方法。
 */
@Service
public class SaleService {

    @Autowired
    private SaleMapper saleMapper;


    /**
     * 获取所有药店信息并分页，name不为空则模糊查询
     * @param pn
     * @param size
     * @param name
     */
    public PageInfo<Sale> getSaleWithPage(Integer pn, Integer size, String name) {
        if (pn == null && size == null) {
            pn = 1;
            size = 0;
        }
        PageHelper.startPage(pn, size);
        List<Sale> list = saleMapper.getAllSale(name);
        PageInfo<Sale> info = new PageInfo<>(list,5);
        return info;
    }

    /**
     * 根据id查找一个药店
     * @param id
     * @return
     */
    public Msg getSaleById(Integer id) {
        Sale sale = saleMapper.getSaleById(id);

        if(sale == null) {
            return Msg.fail().mess("没有找到");
        }
        return Msg.success().data("sale", sale);
    }



}
