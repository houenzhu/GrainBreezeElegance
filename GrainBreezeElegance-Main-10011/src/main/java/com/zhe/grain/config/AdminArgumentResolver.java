package com.zhe.grain.config;

import com.zhe.grain.domain.user.AdminEntity;
import com.zhe.grain.utils.ThreadLocalUtil;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @version 1.0
 * @Author 朱厚恩
 * AdminEntity参数解析器 这样就不需要频繁的获取admin对象
 */

@Component
public class AdminArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == AdminEntity.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        // 从本地线程拿admin对象并注入有AdminEntity参数的方法
        return ThreadLocalUtil.getThreadLocalVal();
    }
}
