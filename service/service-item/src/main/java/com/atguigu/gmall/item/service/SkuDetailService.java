package com.atguigu.gmall.item.service;

import com.atguigu.gmall.model.to.SkuDetailTo;

import java.util.List;

/**
 * @author feng
 * @create 2022-08-26 19:55
 */
public interface SkuDetailService {
    SkuDetailTo getSkuDetail(long skuId);

    void updateHotScore(long skuId);
}
