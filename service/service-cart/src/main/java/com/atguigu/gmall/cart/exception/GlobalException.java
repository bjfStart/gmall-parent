package com.atguigu.gmall.cart.exception;

/**
 * @author feng
 * @create 2022-09-09 0:29
 */
//告诉SpringBoot 这是所有 @Controller 的统一切面
// @RestController

import com.atguigu.gmall.common.execption.GmallException;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 收到所有Controller的异常
  */
//@ResponseBody
//@ControllerAdvice
//@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(GmallException.class)
    public Result handleGmallException(GmallException exception){
        //业务状态的枚举类
        ResultCodeEnum codeEnum = exception.getCodeEnum();
        Result<String> result = Result.build("",codeEnum);
        return result; //前端的返回
    }

    @ExceptionHandler(NullPointerException.class)
    public String handlenullException(NullPointerException gmallException){
        return "haha"; //给前端的返回
    }
}
