package com.atguigu.gmall.order.controller;

import com.atguigu.gmall.common.auth.AuthUtils;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.order.OrderDetail;
import com.atguigu.gmall.model.order.OrderInfo;
import com.atguigu.gmall.model.vo.order.OrderSubmitVo;
import com.atguigu.gmall.order.biz.OrderBizService;
import com.atguigu.gmall.order.service.OrderDetailService;
import com.atguigu.gmall.order.service.OrderInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author feng
 * @create 2022-09-13 22:07
 */
@RestController
@RequestMapping("/api/order/auth")
public class OrderRestController {
    @Autowired
    OrderBizService orderBizService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    OrderInfoService orderInfoService;
    /**
     * 提交订单
     * @param tradeNo
     * @return
     */
    @PostMapping("/submitOrder")
    public Result submitOrder(@RequestParam("tradeNo") String tradeNo,
                              @RequestBody OrderSubmitVo submitVo){

        Long orderId = orderBizService.submitOrder(submitVo,tradeNo);
        return Result.ok(orderId.toString());
    }

    @GetMapping("/{pn}/{ps}")
    public Result orderList(@PathVariable("pn") Long pn,@PathVariable("ps") Long ps){
        Long userId = AuthUtils.getCurrentAuthInfo().getUserId();
        Page<OrderInfo> page = new Page<>(pn, ps);
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        //1.查询orderInfo
        Page<OrderInfo> infoPage = orderInfoService.page(page, wrapper);

        //2.查询orderInfo的所有商品
        infoPage.getRecords().stream()
                .parallel()
                .forEach(orderInfo -> {
                    //查询订单详情
                    List<OrderDetail> orderDetails = orderDetailService.getOrderDetails(orderInfo.getId(), orderInfo.getUserId());
                    orderInfo.setOrderDetailList(orderDetails);
                });
        return Result.ok(infoPage);


    }
}
