package com.zhe.grain.gateway.utils;

import com.alibaba.fastjson.JSON;
import com.zhe.grain.utils.Result;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public class MonoResponseUtil {

    // flux响应
    public static Mono<Void> monoResponse(ServerHttpResponse response, Result<Object> result) {
        response.getHeaders().add("Content-Type", "application/json");
        String jsonString = JSON.toJSONString(result);
        DataBuffer dataBuffer = response.bufferFactory().wrap(jsonString.getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }
}
