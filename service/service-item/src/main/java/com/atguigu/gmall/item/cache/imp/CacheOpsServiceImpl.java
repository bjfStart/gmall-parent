package com.atguigu.gmall.item.cache.imp;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.item.cache.CacheOpsService;
import com.atguigu.gmall.model.to.SkuDetailTo;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


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

    /**
     * 看布隆过滤器中是否包含
     * @param skuId
     * @return
     */
    @Override
    public boolean bloomContains(long skuId) {
        RBloomFilter<Object> filter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        return filter.contains(skuId);
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

    /**
     * 将回源查出来的数据，存入reids中 缓存起来
     * @param cacheKey
     * @param fromRpc
     */
    @Override
    public void saveData(String cacheKey, SkuDetailTo fromRpc) {
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
}
