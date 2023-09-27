package com.example.backendsmartcities.exception;
/**
 * Author: Badreddine TIRGANI
 */
public class TeamServiceException extends Exception {
    public TeamServiceException(String message) {
        super(message);
    }

    public TeamServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}