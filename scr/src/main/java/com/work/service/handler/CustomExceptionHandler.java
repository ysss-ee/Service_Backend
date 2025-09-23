package com.work.service.handler;

import com.work.service.Result.AjaxResult;
import com.work.service.exception.ApiException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(50)
public class CustomExceptionHandler {
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public AjaxResult<Object> handleApiException(ApiException e) {
        return AjaxResult.error(e.getErrorCode(), e.getErrorMsg());
    }


}
