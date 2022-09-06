package com.atguigu.gmall.user.service.impl;

import com.atguigu.gmall.user.service.ActivityRuleService;
import com.atguigu.gmall.user.mapper.ActivityRuleMapper;
import com.atguigu.gmall.model.activity.ActivityRule;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Feng
* @description 针对表【activity_rule(优惠规则)】的数据库操作Service实现
* @createDate 2022-08-25 23:38:46
*/
@Service
public class ActivityRuleServiceImpl extends ServiceImpl<ActivityRuleMapper, ActivityRule>
    implements ActivityRuleService{

    @Override
    public List<ActivityRule> findActivityRuleList(long id) {
        LambdaQueryWrapper<ActivityRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivityRule::getActivityId,id);
        return this.list(queryWrapper);
    }
}




