package cn.rj.hyhealthbackend.domain;

import cn.rj.hyhealthbackend.domain.superdomain.SuperDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author 李柯嘉、陈亮
 * <p>
 * 销售地点实体类Sale
 * - 添加经纬度和地址信息，并添加对应的get/set和构造方法
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Sale extends SuperDomain {
    private Long saleId;//药店id
    private String saleName;//药店名
    private String salePhone;//药店电话
    private Date createtime;//创建时间
    private Date updatetime;//修改时间
    private Double lng;
    private Double lat;
    private String address;
}