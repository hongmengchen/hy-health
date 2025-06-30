package cn.rj.hyhealthbackend.model;

import cn.rj.hyhealthbackend.domain.Account;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author 陈亮
 */
@Getter
public class AccountModel extends Account implements UserDetails {
    private String urealName;//用户真实姓名
    private Collection<? extends GrantedAuthority> authorities;//认证

    public AccountModel() {
    }
    public AccountModel(Long id, String uname, String realname, String pwd, String utype, Collection<? extends GrantedAuthority> authorities) {
        this.setId(id);
        this.setUname(uname);
        this.setPwd(pwd);  // 设置密码字段
        this.setUtype(utype);
        this.urealName = realname;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.getPwd();
    }

    @Override
    public String getUsername() {
        return this.getUname();
    }

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
}
