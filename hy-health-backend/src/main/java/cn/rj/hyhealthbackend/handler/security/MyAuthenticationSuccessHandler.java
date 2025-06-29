package cn.rj.hyhealthbackend.handler.security;

import cn.rj.hyhealthbackend.model.AccountInfoModel;
import cn.rj.hyhealthbackend.model.AccountModel;
import cn.rj.hyhealthbackend.util.JwtUtils;
import cn.rj.hyhealthbackend.util.Msg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 陈亮
 * <p>
 * 登录成功处理器
 * 实现AuthenticationSuccessHandler并重写onAuthenticationSuccess方法，当用户登录成功时调用此处理器，获取用户登录的信息并生成对应的jwtToken和用户基本信息数据并返回给前端数据
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("text/json;charset=utf-8");
        AccountModel model = (AccountModel) authentication.getPrincipal();
        String jwtToken = JwtUtils.getJwtToken(model.getId(), model.getUname(), model.getUtype());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AccountInfoModel info = new AccountInfoModel();
        info.setRealname(model.getUrealName());
        info.setUtype(model.getUtype());
        info.setUtype(info.getUtype().substring("ROLE_".length()));
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(Msg.success().mess("登陆成功").data("token", jwtToken).data("userInfo", info)));
    }
}
