package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.ValueSkuJsonTo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Feng
* @description 针对表【spu_sale_attr(spu销售属性)】的数据库操作Mapper
* @createDate 2022-08-24 09:21:22
* @Entity com.atguigu.gmall.product.domain.SpuSaleAttr
*/
public interface SpuSaleAttrMapper extends BaseMapper<SpuSaleAttr> {

    List<SpuSaleAttr> getSaleAttrAndValueBySupId(long spuId);

    List<SpuSaleAttr> getSaleAttrAndValueMarkSku(@Param("spuId") Long spuId,@Param("skuId") Long skuId);

    List<ValueSkuJsonTo> getAllSkuValueJson(Long spuId);
}




