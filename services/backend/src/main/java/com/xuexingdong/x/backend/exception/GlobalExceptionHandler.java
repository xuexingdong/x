package com.xuexingdong.x.backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public String badRequest(ConstraintViolationException ex) {
        logger.error("{}", ex.getMessage());
        return ex.getConstraintViolations().iterator().next().getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public String badRequest(BindException ex) {
        logger.error("{}", ex.getBindingResult().getFieldError().getDefaultMessage());
        return ex.getBindingResult().getFieldError().getDefaultMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String badRequest(MethodArgumentNotValidException ex) {
        logger.error("{}", ex.getMessage());
        return ex.getBindingResult().getFieldError().getDefaultMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClientException.class)
    public String badRequest(ClientException ex) {
        logger.error("{}", ex.getMessage());
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public String badRequest(RuntimeException ex) {
        logger.error("{}", ex.getMessage());
        return "系统错误";
    }
}