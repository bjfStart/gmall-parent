package com.atguigu.gmall.item.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.model.to.SkuDetailTo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author feng
 * @create 2022-08-26 19:47
 */
@Api(tags = "三级分类的rpc接口")
@RestController
@RequestMapping("/api/inner/rpc/item")
public class SkuDetailApiController {

    @Autowired
    SkuDetailService skuDetailService;

    @GetMapping("/skudetail/{skuId}")
    public Result<SkuDetailTo> getSkuDetail(@PathVariable("skuId") long skuId){
        //商品的详情
        SkuDetailTo skuDetailTo = skuDetailService.getSkuDetail(skuId);

        //更新热度分。攒一批更新一下啊
        skuDetailService.updateHotScore(skuId);

        return Result.ok(skuDetailTo);

    }
}
