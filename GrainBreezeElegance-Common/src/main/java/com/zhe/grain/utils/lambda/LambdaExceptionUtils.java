package com.zhe.grain.utils.lambda;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public class LambdaExceptionUtils {
    public static <T, R> Function<T, R> warp(CheckFunction<T, R> checkFunction) {
        return t -> {
            try {
                return checkFunction.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

}
