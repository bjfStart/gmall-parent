package com.atguigu.gmall.pay;

import com.atguigu.gmall.common.annotation.EnableAutoExceptionHandler;
import com.atguigu.gmall.common.annotation.EnableAutoFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author feng
 * @create 2022-09-16 20:33
 */
@EnableAutoExceptionHandler
@EnableFeignClients({
        "com.atguigu.gmall.feign.order"
})
@EnableAutoFeignInterceptor
@SpringCloudApplication
public class PayMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayMainApplication.class,args);
    }
}
