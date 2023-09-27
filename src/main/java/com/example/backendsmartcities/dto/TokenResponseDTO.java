package com.example.backendsmartcities.dto;
/**
 * Author: Badreddine TIRGANI
 */
public class TokenResponseDTO {
    public static final String ACCESS_TOKEN_KEY = "access-token";
    public static final String REFRESH_TOKEN_KEY = "refresh-token";

    private String accessToken;
    private String refreshToken;
    private String errorMessage;

    public TokenResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenResponseDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
