package com.zhe.grain.handler;

import com.zhe.grain.exception.PermissionException;
import com.zhe.grain.service.user.LoginUserService;
import com.zhe.grain.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 *
 */
@RestControllerAdvice("com.zhe.grain.controller")
@Slf4j
public class GrainExceptionControllerAdvice {

    private final LoginUserService loginUserService;

    @Autowired
    public GrainExceptionControllerAdvice(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result<Object> handleValidException(MethodArgumentNotValidException e) {
        Map<String, Object> errorMap = new HashMap<>();
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return Result.error("参数校验失败", errorMap);
    }

    // 处理其他未知的异常
    // Throwable顶级异常类
    @ExceptionHandler({Throwable.class})
    public Result<Object> handleException(Throwable throwable) {
        log.error(throwable.getMessage());
        return Result.error();
    }

    /**
     * 捕获到全局异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(PermissionException.class)
    public Result<Object> handlePermissionException(PermissionException e) {
        log.error(e.getMessage());
        // 将当前登录的用户强制注销, 重新登录
        loginUserService.logout();
        return Result.error(e.getMessage());
    }
}
