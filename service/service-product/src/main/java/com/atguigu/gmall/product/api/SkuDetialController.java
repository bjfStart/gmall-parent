package com.atguigu.gmall.product.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.atguigu.gmall.product.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author feng
 * @create 2022-08-26 19:58
 */
@RestController
@RequestMapping("/api/inner/rpc/product")
public class SkuDetialController {

    @Autowired
    SkuInfoService skuInfoService;

    @GetMapping("/skudetail/{skuId}")
    public Result<SkuDetailTo> getSkuDetail(@PathVariable("skuId") Long skuId){
        //准备需要查询的数据
        SkuDetailTo skuDetailTo = skuInfoService.getAllSkuDetail(skuId);
        return Result.ok(skuDetailTo);
    }
}
