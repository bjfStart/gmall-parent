package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author feng
 * @create 2022-08-25 0:29
 */
@RestController
@RequestMapping("/admin/product")
public class BaseTradeMarkController {
    @Autowired
    BaseTrademarkService baseTrademarkService;

    //    /admin/product/baseTrademark/1/10

    /**
     * 分页查询所有的品牌
     *
     * @param pn   当前页
     * @param size 每页的个数
     * @return
     */
    @GetMapping("baseTrademark/{pn}/{size}")
    public Result baseTrademark(@PathVariable("pn") Long pn,
                                @PathVariable("size") Long size) {
        Page<BaseTrademark> page = new Page<>(pn, size);
        //分页查询
        Page<BaseTrademark> pageResult = baseTrademarkService.page(page);
        return Result.ok(pageResult);
    }
    // /admin/product/baseTrademark/save

    /**
     * 新增品牌 或 修改属性
     *
     * @param baseTrademark
     * @return
     */
    @PostMapping("baseTrademark/save")
    public Result save(@RequestBody BaseTrademark baseTrademark) {
        baseTrademarkService.save(baseTrademark);
        return Result.ok();
    }

    @PutMapping("baseTrademark/update")
    public Result update(@RequestBody BaseTrademark baseTrademark) {
        baseTrademarkService.updateById(baseTrademark);

        return Result.ok();
    }

    /**
     * /admin/product/baseTrademark/get/2
     * 根据id查询品牌信息，并回显
     *
     * @param id
     * @return
     */
    @GetMapping("baseTrademark/get/{id}")
    public Result getById(@PathVariable String id) {
        BaseTrademark baseTrademark = baseTrademarkService.getById(id);
        return Result.ok(baseTrademark);
    }

    /**
     * /admin/product/baseTrademark/remove/2
     * 根据id删除品牌信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("baseTrademark/remove/{id}")
    public Result remove(@PathVariable String id) {
        baseTrademarkService.removeById(id);
        return Result.ok();
    }


}
