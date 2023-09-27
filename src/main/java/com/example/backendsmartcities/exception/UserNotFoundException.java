package com.example.backendsmartcities.exception;
/**
 * Author: Badreddine TIRGANI
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
