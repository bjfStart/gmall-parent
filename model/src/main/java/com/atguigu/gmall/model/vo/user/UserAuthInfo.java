package com.atguigu.gmall.model.vo.user;

import lombok.Data;

/**
 * @author feng
 * @create 2022-09-08 19:08
 */
@Data
public class UserAuthInfo {
    private Long userId; //用户id
    private String userTempId; //临时id
}
