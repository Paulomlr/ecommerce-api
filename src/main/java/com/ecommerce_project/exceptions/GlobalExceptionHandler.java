package com.ecommerce_project.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFoundException(ResourceNotFoundException ex,
                                                                   HttpServletRequest request) {
        String error = "Resource not found";
        HttpStatus status =HttpStatus.BAD_REQUEST;
        StandardError err = StandardError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(error)
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(err, status);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<StandardError> apiException(ApiException ex, HttpServletRequest request) {
        String error = "API Exception";
        HttpStatus status =HttpStatus.BAD_REQUEST;
        StandardError err = StandardError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(error)
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(err, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                         HttpServletRequest request) {
        String error = "Validation error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));

        StandardError err = StandardError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(error)
                .message(message)
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(err, status);
    }
}