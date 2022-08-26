package com.atguigu.gmall.item.service.impl;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.item.fegin.SkuDetailFeginClient;
import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.model.to.SkuDetailTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author feng
 * @create 2022-08-26 19:56
 */
@Service
public class SkuDetailServiceImpl implements SkuDetailService {

    @Autowired
    SkuDetailFeginClient skuDetailFeginClient;

    @Override
    public SkuDetailTo getSkuDetail(long skuId) {
       Result<SkuDetailTo> skuDetailToResult  = skuDetailFeginClient.getSkuDetail(skuId);
       return skuDetailToResult.getData();
    }
}
