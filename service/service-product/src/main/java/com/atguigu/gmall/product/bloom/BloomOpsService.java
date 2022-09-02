package com.atguigu.gmall.product.bloom;

/**
 * @author feng
 * @create 2022-09-01 18:37
 */
public interface BloomOpsService {

    /**
     * 重建指定布隆过滤器
     * @param bloomName
     */
    void rebuildBloom(String bloomName,BloomDataQueryService bloomDataQueryService);
}
