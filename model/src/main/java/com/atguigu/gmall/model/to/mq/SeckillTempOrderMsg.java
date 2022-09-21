package com.atguigu.gmall.model.to.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author feng
 * @create 2022-09-21 0:19
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeckillTempOrderMsg {
    private Long userId;
    private Long skuId;
    private String skuCode; //秒杀码
}
