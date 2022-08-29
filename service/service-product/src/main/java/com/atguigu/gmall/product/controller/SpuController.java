package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SpuImage;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.product.service.SpuImageService;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author feng
 * @create 2022-08-25 19:49
 */
@RestController
@RequestMapping("/admin/product")
public class SpuController {

    @Autowired
    SpuInfoService spuInfoService;

    @Autowired
    SpuImageService spuImageService;

    /**
     * 分页查询所有spu商品属性
     * /admin/product/1/10?category3Id=61
     */
    @GetMapping("{pn}/{size}")
    public Result getSpuPage(@PathVariable long pn,
                             @PathVariable long size,
                             @RequestParam long category3Id){
        Page<SpuInfo> page = new Page<>(pn, size);
        QueryWrapper<SpuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category3_id",category3Id);
        Page<SpuInfo> result = spuInfoService.page(page,queryWrapper);
        return Result.ok(result);
    }

    /**
     * /admin/product/saveSpuInfo
     */
    @PostMapping("saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo spuInfo){
        spuInfoService.saveSpuInfo(spuInfo);

        return Result.ok();
    }

    /**
     * 根据spuId查询所有的spu图片
     * /admin/product/spuImageList/28
     */
    @GetMapping("spuImageList/{spuId}")
    public Result spuImageList(@PathVariable("spuId") long spuId){
        QueryWrapper<SpuImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("spu_id",spuId);
        List<SpuImage> list = spuImageService.list(queryWrapper);
        return Result.ok(list);
    }
}
