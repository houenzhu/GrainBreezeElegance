package com.zhe.grain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 封装通义千问回复的返回结果
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TongYiResultVO implements Serializable {
    private String requestId;
    private Integer inputTokens;
    private Integer outputTokens;
    private Integer totalTokens;
    private String finishReason;
    private String role;
    private String content;
}
