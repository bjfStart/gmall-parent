package com.atguigu.gmall.gateway.filter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * 万物皆流
 * 两种流：
 *  1.单数据流【0，1】Mono
 *  2.多数据流 【1，N】 Flux
 * 流的中断信号
 * @author feng
 * @create 2022-09-06 23:40
 */
public class GlobalAuthFilter {

    public static void main(String[] args) throws InterruptedException {
//        //1.集合
//        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
//        //2.每个元素加2
//        Integer integer = integers.stream()
//                .map((t)->t+2)
//                .reduce((a,b) -> a+b)
//                .get();
//        System.out.println(integer);

        //1.单数据流。数据发布者
//        Mono<Integer> just = Mono.just(1);

        Flux<Long> just = Flux.interval(Duration.ofSeconds(1));

        //2.数据订阅者，感兴趣发布者的数据
        just.subscribe((t)->{
            System.out.println("消费者1:"+t);
        });
        just.subscribe((t)->{
            System.out.println("消费者2:"+t);
        });
        just.subscribe((t)->{
            System.out.println("消费者3:"+t);
        });

        Thread.sleep(10000);
    }
}
