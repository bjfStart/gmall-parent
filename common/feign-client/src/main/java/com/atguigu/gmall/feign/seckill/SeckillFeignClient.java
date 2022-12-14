package com.atguigu.gmall.feign.seckill;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.activity.SeckillGoods;
import com.atguigu.gmall.model.vo.seckill.SeckillOrderConfirmVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author feng
 * @create 2022-09-19 19:33
 */
@RequestMapping("/api/inner/rpc/seckill")
@FeignClient(value = "service-seckill")
public interface SeckillFeignClient {

    @GetMapping("/currentday/goods/list")
    Result<List<SeckillGoods>> getCurrentDaySeckillGoodsList();


    @GetMapping("/good/detail/{skuId}")
     Result<SeckillGoods> getSeckillGood(@PathVariable("skuId") Long skuId);


    /**
     * 获取秒杀确认页数据
     * @param skuId
     * @return
     */
    @GetMapping("/order/confirmVo/{skuId}")
    Result<SeckillOrderConfirmVo> getSeckillOrderConfirmVo(@PathVariable("skuId") Long skuId);

}
