package cn.rj.hyhealthbackend.model;

import lombok.Data;

/**
 * @author 陈亮
 * <p>
 * AccountInfoModel类
 * 用于登录成功时返回给前端用户信息，其中包含realname和utype两个属性
 */
@Data
public class AccountInfoModel {
    private String realname;
    private String utype;
}
