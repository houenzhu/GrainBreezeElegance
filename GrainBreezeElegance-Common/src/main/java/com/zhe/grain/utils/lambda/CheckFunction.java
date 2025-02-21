package com.zhe.grain.utils.lambda;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 异常处理函数式接口
 */

@FunctionalInterface
public interface CheckFunction<T, R> {
    R apply(T t) throws Exception;
}
