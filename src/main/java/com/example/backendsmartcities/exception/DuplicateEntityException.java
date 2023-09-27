package com.example.backendsmartcities.exception;
/**
 * Author: Badreddine TIRGANI
 */
public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String message) {
        super(message);
    }
}