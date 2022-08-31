package com.atguigu.gmall.item.cache;

import com.atguigu.gmall.model.to.SkuDetailTo;

/**
 * @author feng
 * @create 2022-08-31 15:46
 */
public interface CacheOpsService {
    <T>T getCacheData(String cacheKey, Class<T> clz);

    boolean bloomContains(long skuId);

    boolean tryLock(long skuId);

    void saveData(String cacheKey, SkuDetailTo fromRpc);

    void unlock(long skuId);
}
