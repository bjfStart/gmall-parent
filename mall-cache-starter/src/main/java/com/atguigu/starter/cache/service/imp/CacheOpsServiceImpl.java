package com.atguigu.starter.cache.service.imp;

import com.atguigu.starter.cache.constant.SysRedisConst;
import com.atguigu.starter.cache.service.CacheOpsService;
import com.atguigu.starter.cache.utils.Jsons;
import com.fasterxml.jackson.core.type.TypeReference;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * @author feng
 * @create 2022-08-31 15:46
 */
@Service
public class CacheOpsServiceImpl implements CacheOpsService {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redissonClient;

    /**
     * 从缓存中获取一个数据，并转成指定类型的对象
     * @param cacheKey
     * @param clz
     * @param <T>
     * @return
     */
    @Override
    public <T> T getCacheData(String cacheKey, Class<T> clz) {
        String jsonStr = redisTemplate.opsForValue().get(cacheKey);
        //引入null值缓存机制
        if(SysRedisConst.NULL_VAL.equals(jsonStr)){
            return null;
        }
        T t = Jsons.toObj(jsonStr, clz);
        return t;
    }

    @Override
    public Object getCacheData(String cacheKey, Type type) {
        String jsonStr = redisTemplate.opsForValue().get(cacheKey);
        //引入null值缓存机制
        if(SysRedisConst.NULL_VAL.equals(jsonStr)){
            return null;
        }
        //逆转json为Type类型的复杂对象
        Object obj = Jsons.toObj(jsonStr, new TypeReference<Object>() {
            @Override
            public Type getType() {
                return type; //这个时方法带泛型的返回值类型
            }
        });
        return obj;
    }

    /**
     * 看布隆过滤器中是否包含
     * @param skuId
     * @return
     */
    @Override
    public boolean bloomContains(Object skuId) {
        RBloomFilter<Object> filter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        return filter.contains(skuId);
    }

    @Override
    public boolean bloomContains(String bloomName, Object bVal) {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(bloomName);
        return bloomFilter.contains(bVal);
    }

    /**
     * 尝试获取锁
     * @param skuId
     * @return
     */
    @Override
    public boolean tryLock(long skuId) {
        //1.转杯所用key lock:sku:detail:50
        String lockKey = SysRedisConst.LOCK_SKU_DETAIL+skuId;
        RLock lock = redissonClient.getLock(lockKey);
        //2.尝试加锁
        boolean tryLock = lock.tryLock();
        return tryLock;
    }

    @Override
    public boolean tryLock(String lockName) {
        RLock rLock = redissonClient.getLock(lockName);
        return rLock.tryLock();
    }

    /**
     * 将回源查出来的数据，存入reids中 缓存起来
     * @param cacheKey
     * @param fromRpc
     */
    @Override
    public void saveData(String cacheKey, Object fromRpc) {
        if(fromRpc == null){
            //null值缓存短一些
            redisTemplate.opsForValue().set(cacheKey
                    ,SysRedisConst.NULL_VAL
                    ,SysRedisConst.NULL_VAL_TTL
                    ,TimeUnit.SECONDS);
        }else{
            String str = Jsons.toStr(fromRpc);
            redisTemplate.opsForValue().set(
                    cacheKey,
                    str,
                    SysRedisConst.SKUDETAIL_TTL,
                    TimeUnit.SECONDS);
        }
    }

    @Override
    public void unlock(long skuId) {
        String lockKey = SysRedisConst.LOCK_SKU_DETAIL+skuId;
        RLock lock = redissonClient.getLock(lockKey);
        //解掉这把锁
        lock.unlock();
    }

    @Override
    public void unlock(String lockName) {
        RLock rLock = redissonClient.getLock(lockName);
        rLock.unlock(); //redisson 已经防止了删别人的锁

    }
}
