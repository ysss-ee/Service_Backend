package com.work.service.dto;

import com.work.service.constant.ExceptionEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result<T> {
    private Integer code;    // 状态码
    private String msg;      // 消息
    private T data;          // 数据

    // 构造函数
    public Result() {
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 成功响应
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "成功", data);
    }


    public static <T> Result<T> success() {
        return new Result<>(200, "成功", null);
    }

    // 失败响应

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    // 自定义响应
    public static <T> Result<T> error(ExceptionEnum e) {
        return error(e.getErrorCode(), e.getErrorMsg());
    }
}


