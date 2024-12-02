package com.todo.todolist.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleAllExceptions(Exception e, ServletWebRequest request) {
        log.error(e.getMessage(), e);
        return new ApiError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequest().getRequestURI());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(ConstraintViolationException e, ServletWebRequest request) {
        log.error(e.getMessage(), e);
        return new ApiError(e.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequest().getRequestURI());
    }

    @ExceptionHandler(TodoListItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleValidationException(TodoListItemNotFoundException e, ServletWebRequest request) {
        log.error(e.getMessage(), e);
        return new ApiError(e.getMessage(), HttpStatus.NOT_FOUND.value(), request.getRequest().getRequestURI());
    }
}