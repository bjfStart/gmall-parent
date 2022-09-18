package com.atguigu.gmall.model.vo.order;

import lombok.Data;

import java.util.List;

/**
 * @author feng
 * @create 2022-09-18 22:58
 */
@Data
public class OrderWareMapVo {
    private Long orderId;
    private String wareSkuMap; //json 是 OrderWareMapSkuItemVo 的集合
}
