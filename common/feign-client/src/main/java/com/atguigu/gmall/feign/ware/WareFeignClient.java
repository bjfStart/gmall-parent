package com.atguigu.gmall.feign.ware;

import com.atguigu.gmall.feign.ware.callback.WareFeignClientCallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author feng
 * @create 2022-09-13 20:50
 */
//url: 指定请求发送的绝对路径 app.ware-url 获取， 若未获取到，默认http://localhost:9001/
@FeignClient(value = "ware-manage",url="${app.ware-url:http://localhost:9001/}",fallback = WareFeignClientCallback.class)
public interface WareFeignClient {

//    @GetMapping(value = "/all",produces = "text/html;charset=utf-8")
//    String search(@RequestParam("keyword") String keyword);

    /**
     * 查询一个商品是否有库存
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping("/hasStock")
    String hasStock(@RequestParam("skuId") Long skuId,
                    @RequestParam("num") Integer num);
}
