package com.atguigu.gmall.product.schedule;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.product.bloom.BloomDataQueryService;
import com.atguigu.gmall.product.bloom.BloomOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 重建布隆任务
 * @author feng
 * @create 2022-09-01 19:06
 */
@Service
public class RebuildBloomTask {

    @Autowired
    BloomOpsService bloomOpsService;

    @Autowired
    BloomDataQueryService bloomDataQueryService;

    /**
     * 每隔七天重建一次 bitmap更合适
     * 秒 分 时 日 月 周 （年）
     */
    @Scheduled(cron = "* * 3 ? * 3")
    public void rebuild(){
//        System.out.println("xxxx");
        bloomOpsService.rebuildBloom(SysRedisConst.BLOOM_SKUID,bloomDataQueryService);
    }
}
