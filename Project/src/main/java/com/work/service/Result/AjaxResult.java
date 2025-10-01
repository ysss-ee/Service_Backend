package com.work.service.Result;

import com.work.service.constant.ExceptionEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AjaxResult<T> {
    private Integer code;    // 状态码
    private String msg;      // 消息
    private T data;          // 数据

    // 构造函数
    public AjaxResult() {
    }

    public AjaxResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 成功响应
    public static <T> AjaxResult<T> success(T data) {
        return new AjaxResult<>(200, "成功", data);
    }


    public static <T> AjaxResult<T> success() {
        return new AjaxResult<>(200, "成功", null);
    }

    // 失败响应

    public static <T> AjaxResult<T> error(Integer code, String msg) {
        return new AjaxResult<>(code, msg, null);
    }

    // 自定义响应
    public static <T> AjaxResult<T> error(ExceptionEnum e) {
        return error(e.getErrorCode(), e.getErrorMsg());
    }
}
