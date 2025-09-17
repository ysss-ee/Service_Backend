package com.work.service.handler;

import com.work.service.constant.ExceptionEnum;
import com.work.service.dto.Result;
import com.work.service.util.HandlerUtils;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
@Order(10)
public class ValidateExceptionHandler {
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            JsonMappingException.class,
            HttpMessageNotReadableException.class,
            ServletRequestBindingException.class
    })
    @ResponseBody
    public Result<Object> handleVaildateException(Exception e) {
        HandlerUtils.logException(e);
        return Result.error(ExceptionEnum.INVALID_PARAMETER);
    }


    @ExceptionHandler({
            NoResourceFoundException.class,
            HttpRequestMethodNotSupportedException.class
    })
    @ResponseBody
    public Result<Object> handleNoFoundException(Exception e) {
        HandlerUtils.logException(e);
        return Result.error(ExceptionEnum.NOT_FOUND_ERROR);
    }
}

