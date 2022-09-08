package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.feign.cart.CartFeignClient;
import com.atguigu.gmall.model.product.SkuInfo;
import com.sun.jmx.snmp.SnmpUnknownModelLcdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author feng
 * @create 2022-09-08 0:26
 */
@Controller
public class CartController {
    @Autowired
    CartFeignClient cartFeignClient;

    /**
     * 添加商品到购物车
     * @param skuId
     * @param skuNum
     * @param model
     * @return
     */
    @GetMapping("/addCart.html")
    public String addCarthtml(@RequestParam("skuId") Long skuId,
                              @RequestParam("skuNum") Integer skuNum,
                              Model model){
        //SpringMVC每次收到请求后，这个请求默认就和线程绑定好了

        //1.把指定商品添加到购物车
        System.out.println("web-all 获取的用户id:");
        Result<SkuInfo> result = cartFeignClient.addToCart(skuId, skuNum);
        model.addAttribute("skuInfo",result.getData());
        model.addAttribute("skuNum",skuNum);

        return "cart/addCart";
    }

}
