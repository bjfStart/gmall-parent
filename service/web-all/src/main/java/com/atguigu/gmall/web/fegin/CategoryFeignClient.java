package com.atguigu.gmall.web.fegin;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 告诉springboot ，这是一个远程调用的客户端，调用service-product 微服务的功能
 * 远程调用之前 fegin 会自己找到nacos要到 service-product 真的地址
 * @author feng
 * @create 2022-08-26 18:29
 */
@RequestMapping("/api/inner/rpc/product")
@FeignClient(value = "service-product")
public interface CategoryFeignClient {

    @GetMapping("category/tree")
    Result<List<CategoryTreeTo>> getAllCategoryWithTree();
}
