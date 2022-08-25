package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseSaleAttr;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.product.service.BaseSaleAttrService;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author feng
 * @create 2022-08-25 21:00
 */
@RestController
@RequestMapping("/admin/product")
public class BaseSaleAttrController {
    @Autowired
    BaseSaleAttrService baseSaleAttrService;
    @Autowired
    SpuSaleAttrService spuSaleAttrService;

    /**
     * 查询所有的销售属性
     * /admin/product/baseSaleAttrList
     */
    @GetMapping("baseSaleAttrList")
    public Result baseSaleAttrList() {
        List<BaseSaleAttr> list = baseSaleAttrService.list();
        return Result.ok(list);
    }

    /**
     * 根据spuId查询对应的所有销售属性
     * /admin/product/spuSaleAttrList/28
     */
    @GetMapping("spuSaleAttrList/{spuId}")
    public Result spuSaleAttrList(@PathVariable long spuId){
        List<SpuSaleAttr> list =  spuSaleAttrService.getSaleAttrAndValueBySupId(spuId);
        return Result.ok(list);
    }

}
