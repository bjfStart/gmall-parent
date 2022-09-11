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
        Result<Object> result = cartFeignClient.addToCart(skuId, skuNum);
        if(result.isOk()){
            model.addAttribute("skuInfo",result.getData());
            model.addAttribute("skuNum",skuNum);
            return "cart/addCart";
        }else{
            String message = result.getMessage();
            model.addAttribute("msg",result.getData());
            return "cart/error";
        }

    }

    /**
     * 购物车列表页
     * @return
     */
    @GetMapping("/cart.html")
    public String cartHtml(){
        return "cart/index";
    }

    /**
     * 删除购物车中选中的商品
     * @return
     */
    @GetMapping("/cart/deleteChecked")
    public String deleteChecked(){
        /**
         * redirect:重定向
         * forward: 转发
         */
        cartFeignClient.deleteChecked();
        return "redirect:http://cart.gmall.com/cart.html";
    }

}
