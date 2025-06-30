package cn.rj.hyhealthbackend.mapper;

import cn.rj.hyhealthbackend.entity.AccountEntity;
import cn.rj.hyhealthbackend.model.AccountModel;
import org.springframework.stereotype.Component;

/**
 * @author 陈亮
 * <p>
 * AccountMapper 接口
 * 声明通过用户名查询用户信息方法 securityLogin
 */
@Component
public interface AccountMapper {
    // 用户登录 security
    AccountModel securityLogin(String uname);

    /*校验手机号存在*/
    int checkPhone(String phone);

    /*重置密码*/
    int resetPwd(Long id, String pwd);
    /*注册账号*/
    int regist(AccountEntity entity);

    /*更新账户信息*/
    int updateAccount(AccountEntity entity);
}
