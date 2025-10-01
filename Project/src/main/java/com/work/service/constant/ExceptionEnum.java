package com.work.service.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ExceptionEnum {
    INVALID_PARAMETER(200000, "参数错误"),
    RESOURCE_NOT_FOUND(200001, "资源不存在"),
    WRONG_USERNAME_OR_PASSWORD(200002, "用户名或密码错误"),
    PERMISSION_NOT_ALLOWED(200003, "权限不足"),

    NOT_FOUND_ERROR(200404, HttpStatus.NOT_FOUND.getReasonPhrase()),
    SERVER_ERROR(200500, "系统错误, 请稍后重试");

    private final Integer errorCode;
    private final String errorMsg;


}

