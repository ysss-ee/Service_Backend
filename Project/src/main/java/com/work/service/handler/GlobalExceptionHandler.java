package com.work.service.handler;

import com.work.service.Result.AjaxResult;
import com.work.service.constant.ExceptionEnum;
import com.work.service.util.HandlerUtils;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(1000)
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxResult<String> handleException(Exception e) {
        HandlerUtils.logException(e);
        return AjaxResult.error(ExceptionEnum.SERVER_ERROR);
    }

}

