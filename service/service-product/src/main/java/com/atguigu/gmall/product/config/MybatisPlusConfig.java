package com.atguigu.gmall.product.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author feng
 * @create 2022-08-25 18:31
 */
@EnableTransactionManagement //开启基于注解的事务
@Configuration //告诉springboot这是一个配置类
public class MybatisPlusConfig {
    //1.把MybatisPlus的插件主体（总插件）放到容器中
    @Bean
    public MybatisPlusInterceptor interceptor(){
        //插件主题
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        //加入内部的小插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(true); //页码溢出以后，默认就访问最后一页即可

        //分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor);

        return interceptor;


    }
}
