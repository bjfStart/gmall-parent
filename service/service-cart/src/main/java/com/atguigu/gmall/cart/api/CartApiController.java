package com.atguigu.gmall.cart.api;

import com.atguigu.gmall.cart.service.CartService;
import com.atguigu.gmall.common.auth.AuthUtils;
import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.vo.user.UserAuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.Temporal;

/**
 * @author feng
 * @create 2022-09-08 0:21
 */
@Slf4j
@RestController
@RequestMapping("/api/inner/rpc/cart")
public class CartApiController {
    @Autowired
    CartService cartService;

    /**
     * @RequestHeader(value = SysRedisConst.USERID_HEADER,required = false) String userId,
     * @RequestHeader(value = SysRedisConst.USERTEMPID_HEADER,required = false) String userTempId
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping("/addToCart")
    public Result<SkuInfo> addToCart(@RequestParam("skuId") Long skuId,
                                     @RequestParam("num") Integer num) {
//        System.out.println("service-cart 获取到用户的id："+userId);
//        UserAuthInfo authInfo = AuthUtils.getCurrentAuthInfo();
//        log.info("用户id:{},临时id:{}",authInfo.getUserId(), authInfo.getUserTempId());

        SkuInfo skuInfo = cartService.addToCart(skuId,num);

        return Result.ok(skuInfo);
    }

    /**
     * 删除购物车中选中的商品
     * @return
     */
    @DeleteMapping("/deleteChecked")
    public Result deleteChecked(){
        String cartKey = cartService.determinCartKey();
        cartService.deleteChecked(cartKey);
        return Result.ok();
    }
}
