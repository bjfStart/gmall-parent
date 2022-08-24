package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Feng
* @description 针对表【base_attr_info(属性表)】的数据库操作Service
* @createDate 2022-08-22 21:28:49
*/
public interface BaseAttrInfoService extends IService<BaseAttrInfo> {

    List<BaseAttrInfo> getAttrInfoList(long category1Id, long category2Id, long category3Id);

    void saveAttrInfo(BaseAttrInfo baseAttrInfo);
}
