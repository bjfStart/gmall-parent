package com.atguigu.gmall.model.vo.order;

import lombok.Data;

import java.util.List;

/**
 * @author feng
 * @create 2022-09-18 22:59
 */
@Data
public class OrderWareMapSkuItemVo {
    private Long wareId;
    private List<Long> skuIds;
}
