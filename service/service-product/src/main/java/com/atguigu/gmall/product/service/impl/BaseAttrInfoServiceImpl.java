package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.base.BaseEntity;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.mapper.BaseAttrValueMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.atguigu.gmall.product.mapper.BaseAttrInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author Feng
* @description 针对表【base_attr_info(属性表)】的数据库操作Service实现
* @createDate 2022-08-22 21:28:49
*/
@Service
public class BaseAttrInfoServiceImpl extends ServiceImpl<BaseAttrInfoMapper, BaseAttrInfo>
    implements BaseAttrInfoService{
    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;
    @Override
    public List<BaseAttrInfo> getAttrInfoList(long category1Id, long category2Id, long category3Id) {

        return baseAttrInfoMapper.getAttrInfoList(category1Id,category2Id,category3Id);
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        if(baseAttrInfo.getId() != null){
            //如果 baseAttrInfo的id不为null，为修改
            updateBaseAttrInfo(baseAttrInfo);
        }else{ //如果 baseAttrInfo的id为null，为新增
            //保存base_attr_info 表的信息
            addBaseAttrInfo(baseAttrInfo);
        }
    }

    private void addBaseAttrInfo(BaseAttrInfo baseAttrInfo) {
        baseAttrInfoMapper.insert(baseAttrInfo);
        long baseAttrId = baseAttrInfo.getId();
        //根据已经保存base_attr_info表的id值，保存base_attr_value
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        for (BaseAttrValue baseAttrValue : attrValueList) {
            baseAttrValue.setAttrId(baseAttrId);
            baseAttrValueMapper.insert(baseAttrValue);
        }
    }

    /**
     * 1.先修改baseAttrInfo的信息
     * 2.查询所有前端传过来的 所有属性值的id: vids
     *      2.1 先删除
     *          2.1.1 如果传过来的属性值的id的个数(vids.size()>0)大于零  部分删除， 删除数据库有的，但前端没有传过来的
     *          2.1.2 如果 vids.size()=0, 全部删除， 将属性值的对应为传过来的属性id的所有属性值，全部删除
     *      2.2 修改和删除
     *          2.2.1 如果 传过来的属性值中有id的，则进行修改
     *          2.2.2 如果 传过来的属性值中没有id的，则进行新增 ，(注意，将baseAttrInfo的id赋给 属性值的 attr_id)
     * @param baseAttrInfo
     */
    private void updateBaseAttrInfo(BaseAttrInfo baseAttrInfo) {
        //更新baseAttrInfo的信息
        baseAttrInfoMapper.updateById(baseAttrInfo);
        //更新baseAttrValue的信息
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        //1.先删除
        //1.所有前端过来的所有属性值id
        List<Long> vids = new ArrayList<>();
        for (BaseAttrValue baseAttrValue : attrValueList) {
            Long attrId = baseAttrValue.getId();
            if (attrId != null) {
                vids.add(attrId);
            }
        }

        if(vids.size() > 0 ){
            //部分删除
            QueryWrapper<BaseAttrValue> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("attr_id",baseAttrInfo.getId());
            queryWrapper.notIn("id",vids);
            baseAttrValueMapper.delete(queryWrapper);
        }else{
            //全部删除
            QueryWrapper<BaseAttrValue> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("attr_id",baseAttrInfo.getId());
            baseAttrValueMapper.delete(queryWrapper);

        }

        for (BaseAttrValue baseAttrValue : attrValueList) {
            if(baseAttrValue.getId() != null){
                //修改 前端传过来的attrValueId 并且数据库有的 对应的属性
                baseAttrValueMapper.updateById(baseAttrValue);
            }else{
                //新增 前端传过来 并且 数据库没有 属性
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insert(baseAttrValue);
            }
        }

    }

}




