package com.atguigu.gmall.seckill.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.model.order.OrderInfo;
import com.atguigu.gmall.seckill.biz.SeckillBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author feng
 * @create 2022-09-19 14:02
 */
@RequestMapping("/api/activity/seckill/auth")
@RestController
public class SeckillRestController {
    @Autowired
    SeckillBizService seckillBizService;

    /**
     * 生成秒杀码，以便于真实隐藏秒杀地址
     * @param skuId
     * @return
     */
    @GetMapping("/getSeckillSkuIdStr/{skuId}")
    public Result getSeckillCode(@PathVariable("skuId") Long skuId){
       String code =  seckillBizService.generateSeckillCode(skuId);
       return Result.ok(code);
    }

    /**
     * 秒杀预下单：开始秒杀排队
     * @param skuId
     * @param skuIdStr
     * @return
     */
    @PostMapping("/seckillOrder/{skuId}")
    public Result seckillOrder(@PathVariable("skuId") Long skuId,
                               @RequestParam("skuIdStr") String skuIdStr){
        ResultCodeEnum codeEnum = seckillBizService.seckillOrder(skuId,skuIdStr);
        //1.秒杀码是否合法能否进行秒杀
        //2.走整个秒杀流程
        //3.告诉页面结果
        return Result.build("",codeEnum); //响应成功 200 码
    }

    /**
     * 检查秒杀订单的状态
     * @param skuId
     * @return
     */
    @GetMapping("/checkOrder/{skuId}")
    public Result checkOrder(@PathVariable("skuId") Long skuId){
        ResultCodeEnum resultCodeEnum = seckillBizService.checkSeckillOrderStatus(skuId);
        return Result.build("",resultCodeEnum);
    }

    @PostMapping("/submitOrder")
    public Result submitOrder(@RequestBody OrderInfo orderInfo){
        Long orderId = seckillBizService.submitSeckillOrder(orderInfo);
        //响应订单id
        return Result.ok(orderId.toString());
    }


}
