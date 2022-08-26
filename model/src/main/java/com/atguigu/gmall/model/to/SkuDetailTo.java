package com.atguigu.gmall.model.to;

import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author feng
 * @create 2022-08-26 19:51
 */
@Data
public class SkuDetailTo {
    //当前sku所属的分类信息
    private CategoryViewTo categoryViewTo;

    //商品的基本价值
    private SkuInfo skuInfo;

    //实时价格
    private BigDecimal price;

    //spu的所有销售属性列表
    private List<SpuSaleAttr> spuSaleAttrList;

    //valueSkuJson
    private String valuesSkuJson;
}
