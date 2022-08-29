package com.atguigu.gmall.item.fegin;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author feng
 * @create 2022-08-26 19:57
 */
@RequestMapping("/api/inner/rpc/product")
@FeignClient("service-product")
public interface SkuDetailFeginClient {
//    @GetMapping("/skudetail/{skuId}")
//    Result<SkuDetailTo> getSkuDetail(@PathVariable("skuId") Long skuId);

    //1.查询基本信息 skuInfo
    @GetMapping("/skudetail/skuinfo/{skuId}")
    Result<SkuInfo> getSkuInfo(@PathVariable("skuId") Long skuId);

    //2.查询商品图片信息 skuImage
    @GetMapping("/skudetail/skuImage/{skuId}")
    Result<List<SkuImage>> getSkuImage(@PathVariable("skuId") Long skuId);

    //3.查询商品实时价格
    @GetMapping("/skudetail/skuRealPrice/{skuId}")
    Result<BigDecimal> getRealPrice(@PathVariable("skuId") Long skuId);

    //4.查询销售属性名值
    @GetMapping("/skudetail/skuSaleAttr/{skuId}/{spuId}")
    Result<List<SpuSaleAttr>> getSkuSaleAttr(@PathVariable("skuId") Long skuId
            , @PathVariable("spuId") Long spuId);

    //5.查询sku组合 valueJson
    @GetMapping("/skudetail/valuejson/{spuId}")
    Result<String> getSkuValueJson(@PathVariable("spuId") Long spuId);

    //6.查询分类
    @GetMapping("/skudetail/categoryview/{c3Id}")
    Result<CategoryViewTo> getCategoryView(@PathVariable("c3Id") Long c3Id);

}
