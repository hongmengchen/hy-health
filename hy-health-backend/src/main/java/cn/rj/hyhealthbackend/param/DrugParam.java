package cn.rj.hyhealthbackend.param;

import cn.rj.hyhealthbackend.domain.Drug;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 陈亮
 * <p>
 * 药品信息查询模型
 * - 继承Drug，用于接收前端查询参数，添加了售卖该药的药店属性
 */
@Getter
@Setter
public class DrugParam extends Drug {

    private Long[] saleIds;  // 售卖该药品的药店
    //private String publisher;//发布者
}
