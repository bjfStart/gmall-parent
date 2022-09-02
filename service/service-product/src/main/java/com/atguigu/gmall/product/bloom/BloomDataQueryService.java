package com.atguigu.gmall.product.bloom;

import java.util.List;

/**
 * 布隆数据查询服务
 * @author feng
 * @create 2022-09-01 18:53
 */
public interface BloomDataQueryService {
    //模板模式：所有的设计模式：封装、继承、多态

    /**
     * 父类规定好的算法
     * @return
     */
    List queryData();
}
