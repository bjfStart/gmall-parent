package com.atguigu.gmall.product.bloom.impl;

import com.atguigu.gmall.product.bloom.BloomDataQueryService;
import com.atguigu.gmall.product.bloom.BloomOpsService;
import com.atguigu.gmall.product.service.SkuInfoService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feng
 * @create 2022-09-01 18:37
 */
@Service
public class BloomOpsServiceImpl implements BloomOpsService {
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    SkuInfoService skuInfoService;
    /**
     * 重建布隆
     * @param bloomName
     */
    @Override
    public void rebuildBloom(String bloomName, BloomDataQueryService bloomDataQueryService) {
        RBloomFilter<Object> oldBloomFilter = redissonClient.getBloomFilter(bloomName);

        //1.先准备一个新的布隆过滤器，所有东西都初始化好
        String newBloomFilter = bloomName + "_new";
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(newBloomFilter);
        //2.拿到所有的商品id
//        List<Long> allSkuId = skuInfoService.findAllSkuId();
        List list = bloomDataQueryService.queryData();
        //3.初始化新的布隆
        bloomFilter.tryInit(5000000,0.00001);

//        for (Long skuId : allSkuId) {
//            bloomFilter.add(skuId);
//        }

        for (Object skuId : list) {
            bloomFilter.add(skuId);
        }

        //4.新的布隆过滤器初始化完成

        //5.两个交换(redis是单线程的)，大数据量的删除会导致redis的卡死
        //TODO 极致做法使用lua脚本
        oldBloomFilter.rename("bbb_bloom"); //老布隆下线
        bloomFilter.rename(bloomName);  //新布隆上线

        //6.异步删除老布隆 和 中间交换层
        oldBloomFilter.deleteAsync();
        redissonClient.getBloomFilter("bbb_bloom").deleteAsync();
    }
}
