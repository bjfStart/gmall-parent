package com.atguigu.gmall.user.service;

import com.atguigu.gmall.model.activity.ActivityRule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Feng
* @description 针对表【activity_rule(优惠规则)】的数据库操作Service
* @createDate 2022-08-25 23:38:46
*/
public interface ActivityRuleService extends IService<ActivityRule> {

    List<ActivityRule> findActivityRuleList(long id);
}
