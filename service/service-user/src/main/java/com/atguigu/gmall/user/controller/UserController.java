package com.atguigu.gmall.user.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.model.user.UserInfo;
import com.atguigu.gmall.model.vo.user.LoginSuccessVo;
import com.atguigu.gmall.user.service.UserInfoService;
import com.google.j2objc.annotations.AutoreleasePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author feng
 * @create 2022-09-06 22:38
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserInfoService userInfoService;

    /**
     * 用户登录
     * @param userInfo
     * @return
     */
    @PostMapping("/passport/login")
    public Result login(@RequestBody UserInfo userInfo){
        LoginSuccessVo vo = userInfoService.login(userInfo);
        if(vo != null){
            return Result.ok(vo);
        }

        return Result.build("", ResultCodeEnum.LOGIN_ERROR);
    }

    @GetMapping("/passport/logout")
    public Result logout(@RequestHeader("token") String token){
        userInfoService.logout(token);
        return Result.ok();
    }
}
