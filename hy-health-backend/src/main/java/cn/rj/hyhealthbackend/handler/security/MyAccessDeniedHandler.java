package cn.rj.hyhealthbackend.handler.security;

import cn.rj.hyhealthbackend.util.Msg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 陈亮
 * <p>
 * 无权限响应处理器
 * 实现AccessDeniedHandler接口并重写handle方法，当用户向没有权限的接口发送请求时，调用此处理器发送json数据返回前端，提示用户无访问权限
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(Msg.fail().code(10007).mess("无访问权限")));
    }
}
