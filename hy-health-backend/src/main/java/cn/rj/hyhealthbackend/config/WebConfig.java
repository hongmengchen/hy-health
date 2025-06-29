package cn.rj.hyhealthbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author 陈亮
 * <p>
 * MVC 配置类
 * 配置了响应头中的时间为当前时间，并添加了跨域配置，设置了跨域的域名、请求方法以及请求体信息为所有内容，并设置最长跨域时间为 3600毫 秒
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 设置响应头的时间为GMT+8
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                response.setHeader("date", String.valueOf(new Date()));
                return true;
            }
        }).addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
