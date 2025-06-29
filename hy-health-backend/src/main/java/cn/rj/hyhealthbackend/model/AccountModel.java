package cn.rj.hyhealthbackend.model;

import cn.rj.hyhealthbackend.domain.Account;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountModel extends Account implements UserDetails {
    private String urealName;  // 用户真实姓名
    private Collection<? extends GrantedAuthority> authorities;  // 认证集

    // 获取认证信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    // 获取密码
    @Override
    public String getPassword() {
        return "";
    }

    // 获取用户名
    @Override
    public String getUsername() {
        return "";
    }

    // 账户是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 密码是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账户是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
