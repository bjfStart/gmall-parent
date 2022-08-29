package com.atguigu.gmall.item.controller;

import com.atguigu.gmall.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author feng
 * @create 2022-08-29 23:33
 */
@RestController
public class SkuBitMapController {
    /**
     * 同步数据库中所有商品的id占位表示
     * @return
     */
    @GetMapping("/sync/skuid/bitmap")
    public Result syncBitMap(){
        //1.数据库中所有的商品id查询出来
        //2.挨个置位 setbit skuids 50 0
        return Result.ok();
    }
}
