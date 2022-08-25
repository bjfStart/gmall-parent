package com.atguigu.gmall.product.config.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author feng
 * @create 2022-08-25 20:24
 */
@ConfigurationProperties(prefix = "app.minio")
//和配置文件绑定的
//自动把配置文件中 app.minio 下配置的每个属性全部和这个 JavaBean 属性一一对应
@Component
@Data
public class MinioProperties {
    String endpoint;
    String ak;
    String sk;
    String bucketName;

    //以前的代码一个也不改，以后的代码都能用
    //设计模式： 对新增开放，对修改关闭(开闭原则)
}
