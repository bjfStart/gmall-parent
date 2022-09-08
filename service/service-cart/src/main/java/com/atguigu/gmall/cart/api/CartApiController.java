package com.atguigu.gmall.cart.api;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import org.springframework.web.bind.annotation.*;

/**
 * @author feng
 * @create 2022-09-08 0:21
 */
@RestController
@RequestMapping("/api/inner/rpc/cart")
public class CartApiController {

    @GetMapping("/addToCart")
    public Result<SkuInfo> addToCart(@RequestParam("skuId") Long skuId,
                                     @RequestParam("num") Integer num,
                                     @RequestHeader(value = SysRedisConst.USERID_HEADER,required = false) String userId) {
        System.out.println("service-cart 获取到用户的id："+userId);
        //TODO

        return Result.ok();
    }
}
