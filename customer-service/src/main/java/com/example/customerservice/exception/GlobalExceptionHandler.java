package com.example.customerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Bắt lỗi ResourceNotFoundException (cho GET /{id})
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseError> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiResponseError error = new ApiResponseError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(), // 404
                "Not Found",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Bắt lỗi đăng nhập sai (cho PUT /login)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseError> handleBadRequest(IllegalArgumentException ex) {
        ApiResponseError error = new ApiResponseError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), // 400
                "Bad Request",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Bắt các lỗi hệ thống khác (Optional)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseError> handleGlobalException(Exception ex) {
        ApiResponseError error = new ApiResponseError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
