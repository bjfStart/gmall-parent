package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author feng
 * @create 2022-08-24 9:30
 */
@RestController
@RequestMapping("/admin/product")
public class AttrInfoController {

    @Autowired
    BaseAttrInfoService baseAttrInfoService;

    // /admin/product/attrInfoList/2/0/0
    @GetMapping("attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result attrInfoList(@PathVariable("category1Id") long category1Id,
                               @PathVariable("category2Id") long category2Id,
                               @PathVariable("category3Id") long category3Id){
        List<BaseAttrInfo> list =   baseAttrInfoService.getAttrInfoList(category1Id,category2Id,category3Id);
        return Result.ok(list);
    }
}
