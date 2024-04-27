package com.zhe.grain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 数据处理返回json
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success() {
        return new Result<>(ResultMsgEnum.SUCCESS.getCode(),ResultMsgEnum.SUCCESS.getMsg(), null);
    }
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultMsgEnum.SUCCESS.getCode(),ResultMsgEnum.SUCCESS.getMsg(),  data);
    }

    public static <T> Result<T> success(ResultMsgEnum resultMsgEnum, T data) {
        return new Result<>(resultMsgEnum.getCode(), resultMsgEnum.getMsg(), data);
    }

    public static <T> Result<T> error() {
        return new Result<>(ResultMsgEnum.ERROR.getCode(), ResultMsgEnum.ERROR.getMsg(), null);
    }

    public static <T> Result<T> error(T data) {
        return new Result<>(ResultMsgEnum.ERROR.getCode(), ResultMsgEnum.ERROR.getMsg(), data);
    }

    public static <T> Result<T> error(ResultMsgEnum resultMsgEnum, T data) {
        return new Result<>(resultMsgEnum.getCode(), resultMsgEnum.getMsg(), data);
    }

}
