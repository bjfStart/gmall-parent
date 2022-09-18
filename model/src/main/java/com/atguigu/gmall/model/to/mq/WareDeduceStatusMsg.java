package com.atguigu.gmall.model.to.mq;

import lombok.Data;

/**
 * @author feng
 * @create 2022-09-18 20:58
 */
@Data
public class WareDeduceStatusMsg {
    private Long orderId;
    private String status;
}
