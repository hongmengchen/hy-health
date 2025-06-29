package cn.rj.hyhealthbackend.model;

import cn.rj.hyhealthbackend.domain.Account;

import java.util.Date;

/**
 * @author 陈亮
 *
 * 继承Account并实现UserDetails接口
 * 用于进行用户身份验证，其中authorities为用户的认证集
 */
public class AccountModel extends Account implements UserDetails {
	private String urealName; //用户真实姓名
	private Collection<? extends GrantedAuthority> authorities; //认证集

	@Override
	public boolean isAccountNonExpired() {
	return true;
	}
	@Override
 	public boolean isAccountNonLocked() {
 	return true;
 	}
 	@Override
 	public boolean isCredentialsNonExpired() {
 	return true;
 	}
 	@Override
 	public boolean isEnabled() {
 	return true;
 	}

    // 默认构造方法
    public AccountModel() {
    }

    public AccountModel(Long total, Integer pn, Integer size, String keyword) {
        super(total, pn, size, keyword);
    }

    public AccountModel(Long id, String uname, String pwd, String phoneNumber, String utype, Date createtime, Date updatetime, String realname) {
        super(id, uname, pwd, phoneNumber, utype, createtime, updatetime, realname);
    }

    public AccountModel(Long total, Integer pn, Integer size, String keyword, Long id, String uname, String pwd, String phoneNumber, String utype, Date createtime, Date updatetime, String realname) {
        super(total, pn, size, keyword, id, uname, pwd, phoneNumber, utype, createtime, updatetime, realname);
    }

    // 构造方法
    public AccountModel(String urealName, Collection<? extends GrantedAuthority> authorities) {
        this.urealName = urealName;
        this.authorities = authorities;
    }

    public AccountModel(Long total, Integer pn, Integer size, String keyword, String urealName, Collection<? extends GrantedAuthority> authorities) {
        super(total, pn, size, keyword);
        this.urealName = urealName;
        this.authorities = authorities;
    }

    public AccountModel(Long id, String uname, String pwd, String phoneNumber, String utype, Date createtime, Date updatetime, String realname, String urealName, Collection<? extends GrantedAuthority> authorities) {
        super(id, uname, pwd, phoneNumber, utype, createtime, updatetime, realname);
        this.urealName = urealName;
        this.authorities = authorities;
    }

    public AccountModel(Long total, Integer pn, Integer size, String keyword, Long id, String uname, String pwd, String phoneNumber, String utype, Date createtime, Date updatetime, String realname, String urealName, Collection<? extends GrantedAuthority> authorities) {
        super(total, pn, size, keyword, id, uname, pwd, phoneNumber, utype, createtime, updatetime, realname);
        this.urealName = urealName;
        this.authorities = authorities;
    }

    // getter setter
    public String getUrealName() {
        return urealName;
    }

    public void setUrealName(String urealName) {
        this.urealName = urealName;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
