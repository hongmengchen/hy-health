package cn.rj.hyhealthbackend.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 陈亮
 * <p>
 * Swagger 配置类
 * 配置了 Swagger 网页文档显示信息，webApiConfig 方法返回文档需要汇总的信息对象、webApiInfo 方法则定义了网页文档标题、描述，版本等信息
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Medical-API")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();

    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("慧医数字医疗应用系统-API文档")
                .description("本文档描述了慧医数字医疗应用系统的接口定义")
                .version("1.0")
                .contact(new Contact("QST", "http://www.itshixun.com/", "邮箱地址@xx.xx"))
                .build();
    }
}
