package cn.rj.hyhealthbackend.mapper;

import cn.rj.hyhealthbackend.model.AccountModel;
import org.springframework.stereotype.Component;

/**
 * @author 陈亮
 * <p>
 * AccountMapper接口
 * 声明通过用户名查询用户信息方法securityLogin
 */
@Component
public interface AccountMapper {
    /*用户登录security*/
    AccountModel securityLogin(String uname);
}
