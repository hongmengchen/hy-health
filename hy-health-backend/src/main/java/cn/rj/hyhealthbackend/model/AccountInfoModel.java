package cn.rj.hyhealthbackend.model;

/**
 * @author 陈亮
 *
 * AccountInfoModel类
 * 用于登录成功时返回给前端用户信息，其中包含realname和utype两个属性
 */
public class AccountInfoModel {
    private String realname;
	private String utype;

    // getter和setter方法
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }
}
