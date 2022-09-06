package com.atguigu.gmall.user.service;


import com.atguigu.gmall.model.activity.ActivityInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Feng
* @description 针对表【activity_info(活动表)】的数据库操作Service
* @createDate 2022-08-25 23:38:46
*/
public interface ActivityInfoService extends IService<ActivityInfo> {

    void batchRemove(List<Long> ids);
}
