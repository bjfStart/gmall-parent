package com.atguigu.gmall.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author feng
 * @create 2022-09-18 11:59
 */
@Slf4j
@Service
public class RabbitService {
    @Autowired
    StringRedisTemplate redisTemplate;

    public void retryConsumeMsg(Long maxNum, String uniKey, Long messageTag, Channel channel) throws IOException {
        //lua脚本
        Long aLong = redisTemplate.opsForValue().increment(uniKey);
        //重试消费10次
        if(aLong <= 10){
            channel.basicNack(messageTag,false,true);
        }else{
            channel.basicNack(messageTag,false,false); //不如队
            redisTemplate.delete(uniKey);
            //记录到数据库，消费超了10次都未陈工
            log.error("消息:{},{}次消费失败",messageTag,maxNum);
        }
    }
}
