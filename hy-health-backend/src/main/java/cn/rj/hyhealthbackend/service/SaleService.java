package cn.rj.hyhealthbackend.service;

import cn.rj.hyhealthbackend.domain.Sale;
import cn.rj.hyhealthbackend.entity.SaleEntity;
import cn.rj.hyhealthbackend.mapper.SaleMapper;
import cn.rj.hyhealthbackend.util.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    /**
     * 添加一个药店
     * @param sale
     * @return
     */
    public Msg saveSale(Sale sale) {
        Date d = new Date();
        sale.setCreatetime(d);
        sale.setUpdatetime(d);
        SaleEntity se = new SaleEntity();
        BeanUtils.copyProperties(sale, se);
        try {
            int i = saleMapper.saveSale(se);
            if (i > 0) {
                Long num = se.getTotal() % 5 == 0 ? (se.getTotal() / 5) : (se.getTotal() / 5)+1;
                return Msg.success().data("pages",num).mess("添加成功");
            }
            return Msg.fail().mess("添加失败");
        } catch (Exception e) {
            return Msg.fail().mess("公司编号已经存在");
        }
    }

    /**
     * 根据id删除药店信息
     * @param id
     * @return
     */
    public Msg deleteSaleById(Integer id) {
        int i = saleMapper.deleteSaleById(id);
        if (i > 0) {
            return Msg.success().mess("删除成功");
        } else {
            return Msg.fail().mess("删除失败");
        }
    }

    /**
     * 根据id更新药店信息
     * @param sale
     * @return
     */
    public Msg updateSaleById(Long id, Sale sale) {
        sale.setUpdatetime(new Date());
        sale.setSaleId(id);
        int i = saleMapper.updateSaleById(sale);
        if (i > 0) {
            return Msg.success().mess("修改成功");
        }
        return Msg.fail().mess("修改失败");
    }




}
