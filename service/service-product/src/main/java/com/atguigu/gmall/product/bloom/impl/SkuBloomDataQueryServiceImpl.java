package com.atguigu.gmall.product.bloom.impl;

import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.product.bloom.BloomDataQueryService;
import com.atguigu.gmall.product.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feng
 * @create 2022-09-01 18:54
 */
@Service
public class SkuBloomDataQueryServiceImpl implements BloomDataQueryService {
    @Autowired
    SkuInfoService skuInfoService;
    @Override
    public List queryData() {
        return skuInfoService.findAllSkuId();
    }
}
