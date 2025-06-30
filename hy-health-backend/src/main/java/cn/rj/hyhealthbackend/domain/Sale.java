package cn.rj.hyhealthbackend.domain;

import cn.rj.hyhealthbackend.domain.superdomain.SuperDomain;
import lombok.Data;

import java.util.Date;
/**
 * @author 李柯嘉
 * <p>
 * 销售地点实体类Sale
 */
@Data
public class Sale extends SuperDomain {
    private Long saleId;//药店id
    private String saleName;//药店名
    private String salePhone;//药店电话
    private Date createtime;//创建时间
    private Date updatetime;//修改时间
}