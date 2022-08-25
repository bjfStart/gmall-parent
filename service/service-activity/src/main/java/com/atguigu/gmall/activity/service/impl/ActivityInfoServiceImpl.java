package com.atguigu.gmall.activity.service.impl;

import com.atguigu.gmall.activity.service.ActivityInfoService;
import com.atguigu.gmall.activity.mapper.ActivityInfoMapper;
import com.atguigu.gmall.model.activity.ActivityInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
* @author Feng
* @description 针对表【activity_info(活动表)】的数据库操作Service实现
* @createDate 2022-08-25 23:38:46
*/
@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo>
    implements ActivityInfoService{

    @Autowired
    ActivityInfoMapper activityInfoMapper;

    @Override
    public void batchRemove(List<Long> ids) {

        activityInfoMapper.deleteBatchIds(ids);
    }
}




