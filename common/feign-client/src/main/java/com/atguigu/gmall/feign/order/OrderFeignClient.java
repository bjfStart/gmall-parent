package com.atguigu.gmall.feign.order;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.order.OrderInfo;
import com.atguigu.gmall.model.vo.order.OrderConfirmDataVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author feng
 * @create 2022-09-13 18:54
 */
@RequestMapping("/api/inner/rpc/order")
@FeignClient("service-order")
public interface OrderFeignClient {
    /**
     * 获取确认订单页的数据
     *
     * @return
     */
    @GetMapping("/confirm/data")
    Result<OrderConfirmDataVo> getOrderConfirmData();


    @GetMapping("/info/{orderId}")
    Result<OrderInfo> getOrderInfo(@PathVariable("orderId") Long orderId);

    @PostMapping("/seckillorder/submit")
    Result<Long> submitSeckillOrder(@RequestBody OrderInfo info);
}
