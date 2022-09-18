package com.atguigu.gmall.order.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.vo.order.OrderWareMapVo;
import com.atguigu.gmall.model.vo.order.WareChildOrderVo;
import com.atguigu.gmall.order.biz.OrderBizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author feng
 * @create 2022-09-18 22:55
 */
@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderSplitController {
    @Autowired
    OrderBizService orderBizService;
    /**
     * 订单拆分
     * @param params
     * @return
     */
    @PostMapping("/orderSplit")
    public List<WareChildOrderVo> OrderSplit(OrderWareMapVo params){
        log.info("订单执行拆弹:{}",params);
        //TODO 把这个大订单，拆分成两个子订单（保存到数据库）
        return orderBizService.orderSplit(params);
    }
}
