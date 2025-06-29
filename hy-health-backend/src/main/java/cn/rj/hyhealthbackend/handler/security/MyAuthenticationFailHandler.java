package cn.rj.hyhealthbackend.handler.security;

import cn.rj.hyhealthbackend.util.Msg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 陈亮
 * <p>
 * 用户认证失败处理器
 * 实现AuthenticationFailureHandler接口并重写onAuthenticationFailure方法，当用户认证失败时调用此处理器发送json数据返回前端，提示用户认证失败
 */
@Component
public class MyAuthenticationFailHandler implements AuthenticationFailureHandler {
    private static final Logger logger = Logger.getLogger(MyAuthenticationFailHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        logger.info("MyAuthenticationFailHandler.request: " + request);
        logger.info("MyAuthenticationFailHandler.response: " + response);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(Msg.fail().mess("用户名或密码错误")));
    }
}
