package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.BaseCategory3;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Feng
* @description 针对表【base_category3(三级分类表)】的数据库操作Mapper
* @createDate 2022-08-22 19:26:09
* @Entity com.atguigu.gmall.product.BaseCategory3
*/
public interface BaseCategory3Mapper extends BaseMapper<BaseCategory3> {

    CategoryViewTo getCategoryView(Long category3Id);
}




