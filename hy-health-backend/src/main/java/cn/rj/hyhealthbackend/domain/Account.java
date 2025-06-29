package cn.rj.hyhealthbackend.domain;

import cn.rj.hyhealthbackend.domain.superdomain.SuperDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author 陈亮
 *
 * 用户实体类
 * 用户编号id，用户名uname，用户密码pwd，用户手机号phoneNumber，
 * 用户身份utype，更新事件updatetime，创建事件createtime，用户真实姓名realname
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Account extends SuperDomain {
    private Long id;
    private String uname;
    private String pwd;
    private String phoneNumber;
    private String utype;   //取值：ROLE_1：管理员、ROLE_2：医生、ROLE_3：患者
    private Date updatetime;
    private Date createtime;
    private String realname; //真实姓名

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", uname='" + uname + '\'' +
                ", pwd='" + pwd + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", utype='" + utype + '\'' +
                ", updatetime=" + updatetime +
                ", createtime=" + createtime +
                ", realname='" + realname + '\'' +
                '}';
    }
}
