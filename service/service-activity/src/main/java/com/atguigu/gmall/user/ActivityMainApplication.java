package com.atguigu.gmall.user;

import com.atguigu.gmall.common.config.Swagger2Config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Import;

/**
 * @author feng
 * @create 2022-08-25 23:39
 */
@Import({Swagger2Config.class})
@MapperScan("com.atguigu.gmall.*.mapper")
@SpringCloudApplication
public class ActivityMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActivityMainApplication.class,args);
    }
}
