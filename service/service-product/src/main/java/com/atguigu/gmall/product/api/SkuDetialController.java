package com.atguigu.gmall.product.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.atguigu.gmall.product.service.*;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author feng
 * @create 2022-08-26 19:58
 */
@RestController
@RequestMapping("/api/inner/rpc/product")
public class SkuDetialController {

    @Autowired
    SkuInfoService skuInfoService;

    @Autowired
    SkuImageService skuImageService;

    @Autowired
    SpuSaleAttrService spuSaleAttrService;

    @Autowired
    BaseCategory3Service baseCategory3Service;

//    @GetMapping("/skudetail/{skuId}")
//    public Result<SkuDetailTo> getSkuDetail(@PathVariable("skuId") Long skuId){
//        //准备需要查询的数据
//        SkuDetailTo skuDetailTo = skuInfoService.getAllSkuDetail(skuId);
//        return Result.ok(skuDetailTo);
//    }

    //1.查询基本信息 skuInfo
    @GetMapping("/skudetail/skuinfo/{skuId}")
    public Result<SkuInfo> getSkuInfo(@PathVariable("skuId") Long skuId){
        SkuInfo skuInfo = skuInfoService.getDetailSkuInfo(skuId);
        return Result.ok(skuInfo);
    }

    //2.查询商品图片信息 skuImage
    @GetMapping("/skudetail/skuImage/{skuId}")
    public Result<List<SkuImage>> getSkuImage(@PathVariable("skuId") Long skuId){
        List<SkuImage> skuImageList = skuImageService.getSkuImage(skuId);
        return Result.ok(skuImageList);
    }

    //3.查询商品实时价格
    @GetMapping("/skudetail/skuRealPrice/{skuId}")
    public Result<BigDecimal> getRealPrice(@PathVariable("skuId") Long skuId){
        BigDecimal realPrice = skuInfoService.get1010Price(skuId);
        return Result.ok(realPrice);
    }

    //4.查询销售属性名值
    @GetMapping("/skudetail/skuSaleAttr/{skuId}/{spuId}")
    public Result<List<SpuSaleAttr>> getSkuSaleAttr(@PathVariable("skuId") Long skuId
            ,@PathVariable("spuId") Long spuId){
        List<SpuSaleAttr> saleAttrList = spuSaleAttrService.getSaleAttrAndValueMarkSku(spuId,skuId);
        return Result.ok(saleAttrList);
    }

    //5.查询sku组合 valueJson
    @GetMapping("/skudetail/valuejson/{spuId}")
    public Result<String> getSkuValueJson(@PathVariable("spuId") Long spuId){
        String valueJson = spuSaleAttrService.getAllSkuSaleAttrValueJson(spuId);
        return  Result.ok(valueJson);
    }

    //6.查询分类
    @GetMapping("/skudetail/categoryview/{c3Id}")
    public Result<CategoryViewTo> getCategoryView(@PathVariable("c3Id") Long c3Id){
        CategoryViewTo categoryViewTo = baseCategory3Service.getCategoryView(c3Id);
        return Result.ok(categoryViewTo);
    }
}
