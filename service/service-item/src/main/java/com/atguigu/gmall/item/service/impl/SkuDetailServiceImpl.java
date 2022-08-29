package com.atguigu.gmall.item.service.impl;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.item.fegin.SkuDetailFeginClient;
import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author feng
 * @create 2022-08-26 19:56
 */
@Service
public class SkuDetailServiceImpl implements SkuDetailService {
    /**
     * Map作为缓存【本地缓存】：优缺点
     * 优点：
     * 缺点：
     * 1.100w的数据内存够不够
     * private Map<Long,SkuDetailTo> skuCache = new ConcurrentHashmap<>();
     */
    @Autowired
    SkuDetailFeginClient skuDetailFeginClient;

    @Autowired
    ThreadPoolExecutor executor;

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 未缓存优化前
     *
     * @param skuId
     * @return
     */
    public SkuDetailTo getSkuDetailFromRpc(long skuId) {
        //一次远程冲击调用（网路交互比较浪费时间） 查出所有数据直接给我们
//       Result<SkuDetailTo> skuDetailToResult  = skuDetailFeginClient.getSkuDetail(skuId);
//       return skuDetailToResult.getData();

        //同步调用
        //远程调用其实不用等待，各查各的，异步的方式

        //CompletableFuture.runAsync() // CompletableFuture<void> 启动一个下面不用它返回结果的异步任务
        //CompletableFuture.supplyAsync() //CompletableFuture<U>  启动一个用它返回结果的异步任务
        SkuDetailTo skuDetailTo = new SkuDetailTo();

        //1.查询基本信息 skuInfo
        CompletableFuture<SkuInfo> skuInfoFuture = CompletableFuture.supplyAsync(() -> {
            Result<SkuInfo> result = skuDetailFeginClient.getSkuInfo(skuId);
            SkuInfo skuInfo = result.getData();
            skuDetailTo.setSkuInfo(skuInfo);
            return skuInfo;
        }, executor);

        //2.查询商品图片信息 skuImage
        CompletableFuture<Void> imageFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            Result<List<SkuImage>> skuImage = skuDetailFeginClient.getSkuImage(skuId);
            skuInfo.setSkuImageList(skuImage.getData());
        }, executor);

        //3.查询商品实时价格
        CompletableFuture<Void> priceFuture = CompletableFuture.runAsync(() -> {
            Result<BigDecimal> realPrice = skuDetailFeginClient.getRealPrice(skuId);
            skuDetailTo.setPrice(realPrice.getData());
        }, executor);
        //4.查询销售属性名值
        CompletableFuture<Void> saleAttrFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            Result<List<SpuSaleAttr>> skuSaleAttrValues = skuDetailFeginClient.getSkuSaleAttr(skuId, skuInfo.getSpuId());
            skuDetailTo.setSpuSaleAttrList(skuSaleAttrValues.getData());
        }, executor);

        //5.查询sku组合
        CompletableFuture<Void> valueJsonFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            Result<String> skuValueJson = skuDetailFeginClient.getSkuValueJson(skuInfo.getSpuId());
            skuDetailTo.setValuesSkuJson(skuValueJson.getData());
        }, executor);

        //6.查询分类
        CompletableFuture<Void> categoryViewFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            Result<CategoryViewTo> categoryView = skuDetailFeginClient.getCategoryView(skuInfo.getCategory3Id());
            skuDetailTo.setCategoryViewTo(categoryView.getData());
        }, executor);

        /**
         * 异步实际上是：空间换时间：new Thread()
         * 串行：6s
         * 并行：等待一个最长时间，全部任务都能完成
         *      如果异步： new Thread().start();
         *          一个请求进去，直接无脑开6个线程，高并发下直接OOM
         *          一个一炸可能导致整个集群雪崩
         *      不能无脑开线程，很容易资源耗尽，池技术（线程池、连接池、xx池）【资源服用问题】
         *      线程池+阻塞队列：解决资源服务与等待问题
         *
         * 1. CompletableFuture 异步【编排】
         *      启动一个异步任务有多少种方法
         *      1. new Thread().start()
         *      2. Runnable new Thread(runnable).start();
         *      3.Callable 带结果， FutureTask
         *      4.线程池
         *          executor.submit(()->{}); executor.execute(()->{});
         *      5.异步编排 CompletableFuture
         *          CompletableFuture启动异步任务
         *
         */

        CompletableFuture
                .allOf(imageFuture, priceFuture, saleAttrFuture, valueJsonFuture, categoryViewFuture)
                .join();


        return skuDetailTo;

    }

    @Override
    public SkuDetailTo getSkuDetail(long skuId) {
        //1.看缓存中有没有 sku:info:50
        String jsonStr = redisTemplate.opsForValue().get("sku:info:" + skuId);
        if ("x".equals(jsonStr)) {
            //说明以前查过，只不过数据库没有此记录，为了避免再次回源，缓存一个占位符
            return null;
        }
        if (StringUtils.isEmpty(jsonStr)) {
            //2.redis没有缓存数据
            // 2.1 回源。之前可以判断redis中保存的sku的id集合，有没有这个id
            // 防止随机值穿透攻击？
            SkuDetailTo fromRpc = getSkuDetailFromRpc(skuId);
            // 2.2 放入缓存【查到的对象转入json字符保存到redis】
            String cacheJson = "x";
            if (fromRpc != null) {
                cacheJson = Jsons.toStr(fromRpc);
                redisTemplate.opsForValue().set("sku:info:" + skuId, cacheJson, 7, TimeUnit.DAYS);
            } else {
                redisTemplate.opsForValue().set("sku:info:" + skuId, cacheJson, 30, TimeUnit.MINUTES);
            }
            return fromRpc;
        }

        //3.缓存中有，把json转为指定对象
        SkuDetailTo skuDetailTo = Jsons.toObj(jsonStr, SkuDetailTo.class);
        return skuDetailTo;
    }


//    //使用本地缓存
//    public SkuDetailTo getSkuDetail1(long skuId) {
//        //1.先看缓存
//        SkuDetailTo cacheData = skuCache.get(skuId);
//        //2.判断
//        if(cacheData == null){
//            //3.缓存中没有，真正查询【回源（回到数据源头真正检索）】【提高缓存的命中率】
//            // 1- 0/1 : 0%
//            //2 - 1/2: 50%
//            //N - (N-1)/N: 无线接近100%
//            //缓存命中率替身感到100%：预缓存机制
//            SkuDetailTo fromRpc = getSkuDetailFromRpc(skuId);
//            skuCache.put(skuId,fromRpc);
//            return fromRpc;
//        }
//        //4.缓存有
//        return cacheData;
//    }

}