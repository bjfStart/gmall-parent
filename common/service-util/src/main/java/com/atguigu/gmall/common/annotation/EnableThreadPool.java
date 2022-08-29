package com.atguigu.gmall.common.annotation;

import com.atguigu.gmall.common.config.threadpool.AppThreadPoolAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author feng
 * @create 2022-08-28 23:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(AppThreadPoolAutoConfiguration.class)
public @interface EnableThreadPool {

}
