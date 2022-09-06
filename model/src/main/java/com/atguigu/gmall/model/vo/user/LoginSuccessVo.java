package com.atguigu.gmall.model.vo.user;

import lombok.Data;

/**
 * @author feng
 * @create 2022-09-06 22:52
 */
@Data
public class LoginSuccessVo {
    private String token; //用户的令牌
    private String nickName; //用户
}
