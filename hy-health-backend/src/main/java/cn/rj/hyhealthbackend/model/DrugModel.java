package cn.rj.hyhealthbackend.model;

import cn.rj.hyhealthbackend.domain.Drug;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 陈亮
 * <p>
 * 药品信息业务模型
 * - 继承Drug，用于对前端进行数据交互，并添加销售药店集合属性和销售药店字符串属性
 */
@Getter
@Setter
public class DrugModel extends Drug {

    private List<SaleModel> drugSales;  // 销售该药的药店地址（集合类型，有多个）

    private String drugSale;  // 销售该药的药店地址（字符串类型，有多个）

    @Override
    public String toString() {
        return "DrugModel{" +
                "drugSales=" + drugSales +
                ", drugSale='" + drugSale + '\'' +
                '}';
    }
}
