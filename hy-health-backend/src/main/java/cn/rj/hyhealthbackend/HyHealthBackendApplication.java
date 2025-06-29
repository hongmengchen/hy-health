package cn.rj.hyhealthbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 陈亮
 *
 * 启动类
 */

@MapperScan("cn.rj.hyhealthbackend")
@SpringBootApplication
public class HyHealthBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HyHealthBackendApplication.class, args);
    }

}
