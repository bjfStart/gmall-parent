package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.atguigu.gmall.product.mapper.BaseCategory3Mapper;
import com.atguigu.gmall.product.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Feng
 * @description 针对表【sku_info(库存单元表)】的数据库操作Service实现
 * @createDate 2022-08-24 09:21:22
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo>
        implements SkuInfoService {
    @Autowired
    SkuInfoMapper skuInfoMapper;
    @Autowired
    SkuAttrValueService skuAttrValueService;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    SkuImageService skuImageService;

    @Autowired
    BaseCategory3Mapper baseCategory3Mapper;

    @Autowired
    SpuSaleAttrService spuSaleAttrService;

    @Transactional
    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
        //1.先保存sku的主体信息
        skuInfoMapper.insert(skuInfo);
        //返回新增后的id
        Long skuId = skuInfo.getId();

        //2. 保存skuAttrvalue
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        for (SkuAttrValue skuAttrValue : skuAttrValueList) {
            skuAttrValue.setSkuId(skuId);
        }
        skuAttrValueService.saveBatch(skuAttrValueList);

        //3.保存sku销售属性值 skuSaleAttrValue
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
            skuSaleAttrValue.setSkuId(skuId);
            skuSaleAttrValue.setSpuId(skuInfo.getSpuId());
        }
        skuSaleAttrValueService.saveBatch(skuSaleAttrValueList);

        //4.保存sku图片
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        for (SkuImage skuImage : skuImageList) {
            skuImage.setSkuId(skuId);
        }
        skuImageService.saveBatch(skuImageList);

    }


    /**
     * 是否销售（1：是 0：否）
     *
     * @param skuId
     */
    @Override
    public void cancelSale(long skuId) {
        skuInfoMapper.updateSale(skuId, 0);
        //TODO 2、从es中删除这个商品
    }

    @Override
    public void onSale(Long skuId) {
        skuInfoMapper.updateSale(skuId, 1);
        //TODO 2、给es中保存这个商品，商品就能被检索到了
    }

    @Override
    public SkuDetailTo getAllSkuDetail(Long skuId) {
        SkuDetailTo skuDetailTo = new SkuDetailTo();
        //0.查询到skuInfo
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);

        //1. 查询到商品 sku 的基本信息（价格、质量、名字。。） sku_info
        //把查询到的数据放到SkuDetailTo中
        skuDetailTo.setSkuInfo(skuInfo);

        //2. 商品 sku 的图片
        List<SkuImage> skuImageList = skuImageService.getSkuImage(skuId);
        skuInfo.setSkuImageList(skuImageList);

        //3. 商品sku 所属的完成分类信息：base_category1\base_category2\base_category3
        CategoryViewTo categoryViewTo = baseCategory3Mapper.getCategoryView(skuInfo.getCategory3Id());
        skuDetailTo.setCategoryViewTo(categoryViewTo);
        //4. 实时价格查询
        BigDecimal price = get1010Price(skuId);
        skuDetailTo.setPrice(price);
        //5. 商品 sku 所属spu当时定义的所有销售属性值组合（固定好顺序）
        //  spu_sale_attr\spu_sale_attr_value
        //  并标识出当前sku到底spu的那种组合，页面要有高亮框 sku_sale_attr_value
        //查询当前sku对应的spu定义的所有销售属性名和值（固定好顺序）并且标记好当前sku属于哪一种组合
        List<SpuSaleAttr> saleAttrList = spuSaleAttrService.getSaleAttrAndValueMarkSku(skuInfo.getSpuId(),skuId);
        skuDetailTo.setSpuSaleAttrList(saleAttrList);

        //6. 商品 sku 类似推荐
        //7. 商品 sku 介绍【所属的spu的海报】 spu_poster
        //8. 商品 sku 的规格参数 sku_attr_value
        //9. 商品 sku 售后、评论   相关的表
        return skuDetailTo;
    }

    @Override
    public SkuInfo getDetailSkuInfo(Long skuId) {
        return skuInfoMapper.selectById(skuId);
    }

    public  BigDecimal get1010Price(Long skuId) {
        BigDecimal price = skuInfoMapper.getRealPrice(skuId);
        return price;
    }

    @Override
    public List<Long> findAllSkuId() {
        return skuInfoMapper.getAllSkuId();
    }
}




