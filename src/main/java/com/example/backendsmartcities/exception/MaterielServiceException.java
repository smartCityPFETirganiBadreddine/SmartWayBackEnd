package com.example.backendsmartcities.exception;
/**
 * Author: Badreddine TIRGANI
 */
public class MaterielServiceException extends Exception {
    public MaterielServiceException(String message) {
        super(message);
    }

    public MaterielServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}