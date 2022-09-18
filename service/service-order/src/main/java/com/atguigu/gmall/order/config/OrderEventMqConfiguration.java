package com.atguigu.gmall.order.config;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.constant.MqConst;
import org.springframework.amqp.core.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author feng
 * @create 2022-09-15 11:20
 */
@Configuration
public class OrderEventMqConfiguration {
    /**
     * 项目启动发现没有这个交换机就会自动创建
     * 订单交换机
     * @return
     */
    @Bean
    public Exchange orderEventExchange() {
        /**
         * String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
         */
        TopicExchange exchange = new TopicExchange(MqConst.ORDER_EVENT_EXCHANGE, true, false);
        return exchange;
    }

    //订单延迟队列
    @Bean
    public Queue orderDelayQueue() {
        /**
         * String name, boolean durable, boolean exclusive, boolean autoDelete,@Nullable Map<String, Object> arguments
         */
        Map<String, Object> map = new HashMap<>();
        //延迟队列参数
        map.put("x-message-ttl", SysRedisConst.ORDER_CLOSE_TTL * 1000);
        map.put("x-dead-letter-exchange", MqConst.ORDER_EVENT_EXCHANGE);
        map.put("x-dead-letter-routing-key", MqConst.RK_ORDER_DEAD);
        return new Queue(MqConst.ORDER_DELAY_QUEUE, true, false, false, map);
    }

    @Bean
    public Binding orderDelayQueueBinding() {
        /**
         * String destination,目的地
         * DestinationType destinationType, 目的地类型
         * String exchange, String routingKey,交换机
         * @Nullable Map<String, Object> arguments 参数项
         *
         * 这个exchange交换机和这个destinationType类型的目的地（destination）
         * 使用routingKey进行绑定
         */
        return new Binding(MqConst.ORDER_DELAY_QUEUE, Binding.DestinationType.QUEUE, MqConst.ORDER_EVENT_EXCHANGE, MqConst.RK_ORDER_CREATED, null);
    }

    /**
     * 死单队列：保存所有过期订单，需要进行关单
     * @return
     */
    @Bean
    public Queue orderDeadQueue() {
        /**
         * String name, boolean durable, boolean exclusive, boolean autoDelete,@Nullable Map<String, Object> arguments
         */
        return new Queue(MqConst.ORDER_DEAD_QUEUE, true, false, false);

    }

    /**
     * 死单队列和订单时间交换机绑定
     * @return
     */
    @Bean
    public Binding orderDeadQueueBing() {
        /**
         * String destination, DestinationType destinationType,
         *           String exchange, String routingKey,
         *           @Nullable Map<String, Object> arguments
         */
        return new Binding(MqConst.ORDER_DEAD_QUEUE,
                Binding.DestinationType.QUEUE,
                MqConst.ORDER_EVENT_EXCHANGE,
                MqConst.RK_ORDER_DEAD,
                null);
    }

    /**
     * 支付成功单队列
     * @return
     */
    @Bean
    public Queue payedQueue(){
        return new Queue(MqConst.ORDER_PAYED_QUEUE,
                true,
                false,
                false
                );
    }

    @Bean
    public Binding payedQueueBinding(){
        return new Binding(MqConst.ORDER_PAYED_QUEUE,
                Binding.DestinationType.QUEUE,
                MqConst.ORDER_EVENT_EXCHANGE,
                MqConst.RK_ORDER_PAYED,
                null);
    }

}
