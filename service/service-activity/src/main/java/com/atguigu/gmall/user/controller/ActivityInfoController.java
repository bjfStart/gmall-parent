package com.atguigu.gmall.user.controller;

import com.atguigu.gmall.user.service.ActivityInfoService;
import com.atguigu.gmall.user.service.ActivityRuleService;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.activity.ActivityInfo;
import com.atguigu.gmall.model.activity.ActivityRule;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author feng
 * @create 2022-08-25 23:46
 */
@RestController
@RequestMapping("/admin/activity")
public class ActivityInfoController {

    @Autowired
    ActivityInfoService activityInfoService;

    @Autowired
    ActivityRuleService activityRuleService;

    /**
     * 分页查询活动
     * /admin/activity/activityInfo/1/10
     */
    @GetMapping("activityInfo/{pn}/{ps}")
    public Result activityInfo(@PathVariable long pn,
                               @PathVariable long ps){
        Page<ActivityInfo> page = new Page<ActivityInfo>(pn, ps);
        Page<ActivityInfo> result = activityInfoService.page(page);
        return Result.ok(result);
    }

    /**
     * 新增/或修改活动
     * /admin/activity/activityInfo/save
     */
    @PostMapping("activityInfo/save")
    public Result save(@RequestBody ActivityInfo activityInfo){
        activityInfoService.save(activityInfo);
        return Result.ok();
    }

    /**
     * 更新
     * /admin/activity/activityInfo/update
     */
    @PutMapping("activityInfo/update")
    public Result update(@RequestBody ActivityInfo activityInfo){
        activityInfoService.updateById(activityInfo);
        return Result.ok();
    }


    /**
     * 根据活动id回显活动的信息
     * /admin/activity/activityInfo/get/9
     */
    @GetMapping("activityInfo/get/{id}")
    public Result getById(@PathVariable long id){
        ActivityInfo activityInfo = activityInfoService.getById(id);
        return Result.ok(activityInfo);
    }

    /**
     * 根据活动id删除
     * /admin/activity/activityInfo/remove/1
     */
    @DeleteMapping("activityInfo/remove/{id}")
    public Result remove(@PathVariable long id){
        activityInfoService.removeById(id);
        return Result.ok();
    }

    /**
     * 批量删除
     * /admin/activity/activityInfo/batchRemove
     */
    @DeleteMapping("activityInfo/batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids){
        activityInfoService.batchRemove(ids);
        return Result.ok();
    }

    /**
     *  获取对应活动的活动规则
     * 	/admin/activity/activityInfo/findActivityRuleList/10
     */
    @GetMapping("activityInfo/findActivityRuleList/{id}")
    public Result findActivityRuleList(@PathVariable long id){
        List<ActivityRule> list = activityRuleService.findActivityRuleList(id);
        return Result.ok();
    }


}
