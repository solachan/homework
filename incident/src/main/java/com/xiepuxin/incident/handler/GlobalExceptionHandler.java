package com.xiepuxin.incident.handler;

import com.xiepuxin.incident.model.R;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return R.fail(errors);
    }

    @ExceptionHandler(Exception.class)
    public R<Map<String, String>> handleValidationExceptions(Exception ex) {
        //其他异常统一处理
        return R.fail(ex.getMessage());
    }
}
