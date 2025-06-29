package cn.rj.hyhealthbackend.domain;

import cn.rj.hyhealthbackend.domain.superdomain.SuperDomain;

import java.util.Date;

/**
 * @author 陈亮
 *
 * 用户实体类
 * 用户编号id，用户名uname，用户密码pwd，用户手机号phoneNumber，
 * 用户身份utype，更新事件updatetime，创建事件createtime，用户真实姓名realname
 */
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

    // 构造函数
    public Account() {
    }

    public Account(Long total, Integer pn, Integer size, String keyword) {
        super(total, pn, size, keyword);
    }

    public Account(Long id, String uname, String pwd, String phoneNumber, String utype, Date createtime, Date updatetime, String realname) {
        this.id = id;
        this.uname = uname;
        this.pwd = pwd;
        this.phoneNumber = phoneNumber;
        this.utype = utype;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.realname = realname;
    }

    public Account(Long total, Integer pn, Integer size, String keyword, Long id, String uname, String pwd, String phoneNumber, String utype, Date createtime, Date updatetime, String realname) {
        super(total, pn, size, keyword);
        this.id = id;
        this.uname = uname;
        this.pwd = pwd;
        this.phoneNumber = phoneNumber;
        this.utype = utype;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.realname = realname;
    }

    // getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
