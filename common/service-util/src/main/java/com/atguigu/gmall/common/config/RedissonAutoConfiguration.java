package com.atguigu.gmall.common.config;

import io.lettuce.core.RedisClient;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author feng
 * @create 2022-08-31 22:17
 */
@AutoConfigureAfter(RedisAutoConfiguration.class)
@Configuration
public class RedissonAutoConfiguration {
    @Autowired
    RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient(){
        //1.创建一个配置
        Config config = new Config();
        String host = redisProperties.getHost();
        int port = redisProperties.getPort();
        String password = redisProperties.getPassword();
        //2.指定好redisson 的配置项
        config.useSingleServer()
                .setAddress("redis://"+host+":"+port)
                .setPassword(password);
        //3.创建一个RedissonClient
        RedissonClient client = Redisson.create(config);

        return client;

    }
}
