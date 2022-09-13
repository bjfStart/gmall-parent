package com.atguigu.gmall.model.vo.order;

import com.atguigu.gmall.model.cart.CartInfo;
import lombok.Data;

import java.util.List;

/**
 * 提单提交 数据模型
 * @author feng
 * @create 2022-09-13 22:10
 */
@Data
public class OrderSubmitVo {
    private String consignee;
    private String consigneeTel;
    private String deliveryAddress;
    private String paymentWay;
    private String orderComment;
    private List<CartInfoVo> orderDetailList;
}
