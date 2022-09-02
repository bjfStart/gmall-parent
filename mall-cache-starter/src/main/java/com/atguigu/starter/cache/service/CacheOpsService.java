package com.atguigu.starter.cache.service;


import java.lang.reflect.Type;

/**
 * @author feng
 * @create 2022-08-31 15:46
 */
public interface CacheOpsService {
    /**
     * 从缓存中获取一个json并转为普通对象
     * @param cacheKey
     * @param clz
     * @param <T>
     * @return
     */
    <T>T getCacheData(String cacheKey, Class<T> clz);

    /**
     * 从缓存中获取一个json并转为复杂对象
     * @param cacheKey
     * @param type
     * @return
     */
    Object getCacheData(String cacheKey, Type type);

    boolean bloomContains(Object skuId);

    /**
     * 判定指定布隆过滤器（bloomName） 是否包含指定值（bVal）
     * @param skuId
     * @param bVal
     * @return
     */
    boolean bloomContains(String bloomName,Object bVal);

    boolean tryLock(long skuId);

    /**
     * 加指定的分布式锁
     * @param lockName
     * @return
     */
    boolean tryLock(String lockName);

    void saveData(String cacheKey, Object fromRpc);

    void unlock(long skuId);

    /**
     * 解指定的锁
     * @param lockName
     */
    void unlock(String lockName);
}
