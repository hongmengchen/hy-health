package cn.rj.hyhealthbackend.service.impl;

import cn.rj.hyhealthbackend.mapper.AccountMapper;
import cn.rj.hyhealthbackend.model.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 陈亮
 * <p>
 * UserDetailsServiceImpl 实现类
 * 实现UserDetasService接口并重写lodaUserByUsername，用于对登录用户进行验证以及权限获取，如果不存在则抛出用户不存在异常，否则将用户权限集赋给用户
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AccountModel accountModel = accountMapper.securityLogin(s);
        if (accountModel == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        System.out.println("用户名: 【" + accountModel.getUname() + "】");
        String encodedPassword = accountModel.getPwd();
        System.out.println("加密密码: 【" + encodedPassword + "】");
        if (encodedPassword == null || !encodedPassword.startsWith("$2a$")) {
            throw new UsernameNotFoundException("密码格式不正确");
        }

        String role = accountModel.getUtype();
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(role);

        return new AccountModel(
                accountModel.getId(),
                accountModel.getUname(),
                accountModel.getRealname(),
                encodedPassword,
                role,
                auths);
    }
}
