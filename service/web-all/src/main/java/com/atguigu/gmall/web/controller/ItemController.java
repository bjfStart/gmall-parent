package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.atguigu.gmall.web.fegin.SkuDetailFeginClient;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author feng
 * @create 2022-08-26 21:30
 */
@Api(tags = "查询商品详情")
@Controller
public class ItemController {

    @Autowired
    SkuDetailFeginClient skuDetailFeginClient;

    @GetMapping("/{skuId}.html")
    public String item(@PathVariable long skuId, Model model){
        Result<SkuDetailTo> result = skuDetailFeginClient.getSkuDetail(skuId);
        if(result.isOk()){
            SkuDetailTo skuDetailTo = result.getData();
            //成功
            model.addAttribute("categoryView",skuDetailTo.getCategoryViewTo());
            model.addAttribute("skuInfo",skuDetailTo.getSkuInfo());
            model.addAttribute("price",skuDetailTo.getPrice());
            model.addAttribute("spuSaleAttrList",skuDetailTo.getSpuSaleAttrList());
            model.addAttribute("valuesSkuJosn",skuDetailTo.getValuesSkuJson());
        }
        return "item/index";
    }

}
