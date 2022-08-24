package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.base.BaseEntity;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.atguigu.gmall.product.service.BaseAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author feng
 * @create 2022-08-24 9:30
 */
@RestController
@RequestMapping("/admin/product")
public class BaseAttrInfoController {

    @Autowired
    BaseAttrInfoService baseAttrInfoService;

    @Autowired
    BaseAttrValueService baseAttrValueService;

    // /admin/product/attrInfoList/2/0/0
    @GetMapping("attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result attrInfoList(@PathVariable("category1Id") long category1Id,
                               @PathVariable("category2Id") long category2Id,
                               @PathVariable("category3Id") long category3Id){
        List<BaseAttrInfo> list =   baseAttrInfoService.getAttrInfoList(category1Id,category2Id,category3Id);
        return Result.ok(list);
    }

    /**
     * 更新并修改
     * @param baseAttrInfo
     * @return
     */
//    /admin/product/saveAttrInfo
    @PostMapping("saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        baseAttrInfoService.saveAttrInfo(baseAttrInfo);

        return Result.ok();
    }


    //修改平台属性前的数据回显
//    /admin/product/getAttrValueList/11
    @GetMapping("getAttrValueList/{baseAttrId}")
    public Result getAttrValueList(@PathVariable("baseAttrId") long baseAttrId){
        List<BaseAttrValue> list = baseAttrValueService.getAttrValueList(baseAttrId);
        return Result.ok(list);
    }
}
