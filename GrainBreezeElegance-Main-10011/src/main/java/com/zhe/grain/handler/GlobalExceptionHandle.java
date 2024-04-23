package com.zhe.grain.handler;

import com.zhe.grain.exception.LoginException;
import com.zhe.grain.vo.Result;
import com.zhe.grain.vo.ResultMsgEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 全局异常捕获
 */

@RestControllerAdvice
public class GlobalExceptionHandle {

    /**
     * 出现大异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result<String> globalException(Exception e) {
        return Result.error(ResultMsgEnum.ERROR, null);
    }

    @ExceptionHandler(value = LoginException.class)
    public Result<String> loginException(LoginException e) {
        System.out.println(e.getMessage());
        return Result.error(ResultMsgEnum.LOGIN_ERROR, null);
    }
}
