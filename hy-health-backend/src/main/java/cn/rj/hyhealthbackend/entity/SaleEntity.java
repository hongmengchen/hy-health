package cn.rj.hyhealthbackend.entity;

import cn.rj.hyhealthbackend.domain.Sale;
import lombok.Data;

/**
 * @author 李柯嘉
 * <p>
 * 销售地点数据模型
 * - 用于对数据库进行交互
 */
@Data
public class SaleEntity extends Sale {

    private Long total;//插入成功后返回的总记录数
}
