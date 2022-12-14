package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
* @author Feng
* @description 针对表【sku_info(库存单元表)】的数据库操作Service
* @createDate 2022-08-24 09:21:22
*/
public interface SkuInfoService extends IService<SkuInfo> {

    void saveSkuInfo(SkuInfo skuInfo);

    void cancelSale(Long skuId);

    void onSale(Long skuId);

    SkuDetailTo getAllSkuDetail(Long skuId);

    SkuInfo getDetailSkuInfo(Long skuId);

    BigDecimal get1010Price(Long skuId);

    /**
     * 找到所有的商品id
     * @return
     */
    List<Long> findAllSkuId();

    /**
     * 得到某个sku在es中需要存储所有数据
     * @param skuId
     * @return
     */
    Goods getGoodsBySkuId(Long skuId);
}
