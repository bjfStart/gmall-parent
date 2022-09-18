package com.atguigu.gmall.model.to.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author feng
 * @create 2022-09-15 14:00
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderMsg {
    private Long orderId;
    private Long userId;
}
