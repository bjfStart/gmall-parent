package com.atguigu.starter.cache.constant;

/**
 * @author feng
 * @create 2022-08-31 15:39
 */
public class SysRedisConst {
    public static final String NULL_VAL = "x";

    public static final String LOCK_SKU_DETAIL = "lock:sku:detail";

    public static final Long NULL_VAL_TTL = 60*60*24*7L;

    public static final Long SKUDETAIL_TTL = 60*60*24*7L;

    public static final String SKU_INFO_PREFIX = "sku:info:";

    public static final String BLOOM_SKUID = "bloom:skuid";

    public static final String PREFIX = "lock:";
}
