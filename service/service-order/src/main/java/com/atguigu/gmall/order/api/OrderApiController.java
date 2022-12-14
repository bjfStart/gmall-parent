package com.atguigu.gmall.order.api;

import com.atguigu.gmall.common.auth.AuthUtils;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.cart.CartInfo;
import com.atguigu.gmall.model.order.OrderInfo;
import com.atguigu.gmall.model.vo.order.OrderConfirmDataVo;
import com.atguigu.gmall.order.biz.OrderBizService;
import com.atguigu.gmall.order.service.OrderInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author feng
 * @create 2022-09-13 18:48
 */
@RestController
@RequestMapping("/api/inner/rpc/order")
public class OrderApiController {

    @Autowired
    OrderBizService orderBizService;

    @Autowired
    OrderInfoService orderInfoService;



    /**
     * 获取确认订单页的数据
     * @return
     */
    @GetMapping("/confirm/data")
    public Result<OrderConfirmDataVo> getOrderConfirmData(){
        OrderConfirmDataVo vo = orderBizService.getConfirmData();

        return Result.ok(vo);
    }

    /**
     * 获取某个订单数据
     * @param orderId
     * @return
     */
    @GetMapping("info/{orderId}")
    public Result<OrderInfo> getOrderInfo(@PathVariable("orderId") Long orderId){
        Long userId = AuthUtils.getCurrentAuthInfo().getUserId();
        LambdaQueryWrapper<OrderInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderInfo::getId,orderId);
        queryWrapper.eq(OrderInfo::getUserId,userId);
        OrderInfo one = orderInfoService.getOne(queryWrapper);
        return Result.ok(one);

    }


}
