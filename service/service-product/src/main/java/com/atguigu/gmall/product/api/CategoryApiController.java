package com.atguigu.gmall.product.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.atguigu.gmall.product.service.BaseCategory2Service;
import com.atguigu.starter.cache.annotation.GmallCache;
import com.atguigu.starter.cache.constant.SysRedisConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类有关的API
 *      以后所有远程调用都是内部接口：命名规范：/api/inner/rpc/模块名/路径
 * @author feng
 * @create 2022-08-26 18:31
 */
@Api(tags = "三级分类的RPC接口")
@RestController
@RequestMapping("/api/inner/rpc/product")
public class CategoryApiController {
    @Autowired
    BaseCategory2Service baseCategory2Service;

    @ApiOperation("三级分类树形结构查询")
    @GetMapping("category/tree")
    public Result getAllCategoryWithTree(){
        List<CategoryTreeTo> list = baseCategory2Service.getAllCategoryWithTree();
        return Result.ok(list);
    }
}
