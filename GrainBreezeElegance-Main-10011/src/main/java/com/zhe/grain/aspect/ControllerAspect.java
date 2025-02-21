package com.zhe.grain.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

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
     * 对控制器做参数校验
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
        if (args.length == 1) {
//            extracted(args);
        } else {
            for (Object arg : args) {
                if (arg instanceof Map<?, ?> map) {
                    if (CollectionUtils.isEmpty(map)) {
                        throw new RuntimeException("参数不能为空!");
                    }
                } else if (arg instanceof List<?> list) {
                    if (CollectionUtils.isEmpty(list)) {
                        throw new RuntimeException("参数不能为空!");
                    }
                } else {
//                    extracted(args);
                }
            }
        }
    }

    private static void extracted(Object[] args) {
        Field[] fields = args[0].getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(args[0]);
                if (Objects.isNull(value) || StringUtils.isBlank(value.toString())) {
                    throw new RuntimeException("用户参数都不能为空!");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("未知异常!");
            }
        }
    }
}
