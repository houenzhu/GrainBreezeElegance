package com.zhe.grain.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Order(1)
@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Pointcut(value = "@annotation(com.zhe.grain.annotation.CheckArgs)")
    public void pointCut() {

    }

    /**
     * 对订单详情做参数校验
     * @param joinPoint
     */
    @Before(value = "pointCut()")
    public void checkParams(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        log.info("调用的方法名: {}, 参数: {}", signature.getName(), joinPoint.getArgs());
        Object[] args = joinPoint.getArgs();
        if (CollectionUtils.isEmpty(Arrays.asList(args))) {
            throw new RuntimeException("参数不能为空!");
        }
    }
}
