package com.atguigu.gmall.common.auth;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.model.vo.user.UserAuthInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author feng
 * @create 2022-09-08 19:22
 */

public class AuthUtils {
    /**
     * 利用 Tomcat请求与线程绑定机制 + spring 自己的RequestContextHolder ThreadLocal 原理 = 同义一个请求再处理期间，任何时候都能共享到数据
     * @return
     */
    public static UserAuthInfo getCurrentAuthInfo(){
        //1.拿到老请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //2. 获取信息
        UserAuthInfo authInfo = new UserAuthInfo();
        String header = request.getHeader(SysRedisConst.USERID_HEADER);
        if(!StringUtils.isEmpty(header)){
            authInfo.setUserId(Long.parseLong(header));
        }
        String tempHeader = request.getHeader(SysRedisConst.USERTEMPID_HEADER);
        authInfo.setUserTempId(tempHeader);
        return authInfo;
    }
}
