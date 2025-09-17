package com.ecommerce_project.exceptions;

public class ApiException extends RuntimeException{
    public ApiException(String message) {
        super(message);
    }
}
