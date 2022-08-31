package com.atguigu.gmall.product;

import com.atguigu.gmall.common.annotation.EnableThreadPool;
import com.atguigu.gmall.common.config.RedissonAutoConfiguration;
import com.atguigu.gmall.common.config.Swagger2Config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Import;

/**
 * @author feng
 * @create 2022-08-22 16:58
 */
@EnableThreadPool
@Import({Swagger2Config.class, RedissonAutoConfiguration.class})
@SpringCloudApplication
@MapperScan(basePackages = "com.atguigu.gmall.*.mapper")
public class ProductMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductMainApplication.class,args);
    }
}
