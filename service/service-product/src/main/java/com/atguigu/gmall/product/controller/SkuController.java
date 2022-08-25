package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author feng
 * @create 2022-08-25 21:43
 */

@RestController
@RequestMapping("/admin/product")
public class SkuController {

    @Autowired
    SkuInfoService skuInfoService;

    /**
     * 保存商品属性 sku
     * /admin/product/saveSkuInfo
     */
    @PostMapping("saveSkuInfo")
    public Result saveSkuInfo(@RequestBody SkuInfo skuInfo){
        skuInfoService.saveSkuInfo(skuInfo);
        return Result.ok();
    }

    /**
     * 分页查询所有的sku
     * /admin/product/list/1/10
     */
    @GetMapping("list/{pn}/{size}")
    public Result pageList(@PathVariable long pn,
                           @PathVariable long size){
        Page<SkuInfo> page = new Page<>(pn, size);
        Page<SkuInfo> result = skuInfoService.page(page);
        return Result.ok(result);
    }

    /**
     * 商品下架
     * /admin/product/cancelSale/48
     */
    @GetMapping("cancelSale/{skuId}")
    public Result cancelSale(@PathVariable long skuId){
        skuInfoService.cancelSale(skuId);
        return Result.ok();
    }

    /**
     * 上架
     * /admin/product/onSale/48
     */
    @GetMapping("onSale/{skuId}")
    public Result onSale(@PathVariable Long skuId){
        skuInfoService.onSale(skuId);
        return Result.ok();
    }
}
