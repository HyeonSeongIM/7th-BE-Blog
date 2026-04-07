package com.leets.blog.controller;

import com.leets.blog.support.error.PostException;
import com.leets.blog.support.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(PostException.class)
    public ResponseEntity<?> handlePostException(PostException e) {
        switch (e.getErrorType().getLogLevel()) {
            case ERROR -> log.error("PostException : {}", e.getMessage(), e);
            case WARN -> log.warn("PostException : {}", e.getMessage(), e);
            default -> log.info("PostException : {}", e.getMessage(), e);
        }
        return new ResponseEntity<>(ApiResponse.error(e.getErrorType(), e.getData()), e.getErrorType().getStatus());
    }
}
