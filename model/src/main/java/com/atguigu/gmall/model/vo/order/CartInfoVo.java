package com.atguigu.gmall.model.vo.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author feng
 * @create 2022-09-13 19:35
 */
@Data
public class CartInfoVo {
    private Long skuId;

    private String imgUrl;

    private String skuName;

    private BigDecimal orderPrice; //实时价格，最新价格

    private Integer skuNum;

    //是否有货
    private String hasStock = "1";
}
