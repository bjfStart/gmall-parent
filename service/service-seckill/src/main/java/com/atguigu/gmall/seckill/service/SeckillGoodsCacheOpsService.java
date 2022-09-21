package com.atguigu.gmall.seckill.service;

import com.atguigu.gmall.model.activity.SeckillGoods;

import java.util.List;

/**
 * @author feng
 * @create 2022-09-19 20:47
 */
public interface SeckillGoodsCacheOpsService {
    List<SeckillGoods> getSeckillGoodsFromLocal();

    /**
     * 上架秒杀的商品
     * @param list
     */
    void upSeckillGoods(List<SeckillGoods> list);

    /**
     * 清缓存
     */
    void clearCache();

    /**
     * n
     */
    void syncLocalAndRedisCache();

    /**
     * 查询redis获取秒杀商品
     * @return
     */
    List<SeckillGoods> getSeckillGoodsFromRemote();

    SeckillGoods getSeckillGoodsDetail(Long skuId);
}
