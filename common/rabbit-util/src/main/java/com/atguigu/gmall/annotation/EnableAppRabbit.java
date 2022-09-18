package com.atguigu.gmall.annotation;

import com.atguigu.gmall.rabbit.AppRabbitConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author feng
 * @create 2022-09-15 10:35
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(AppRabbitConfiguration.class)
public @interface EnableAppRabbit {
}
