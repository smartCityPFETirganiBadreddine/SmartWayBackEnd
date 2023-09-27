package com.example.backendsmartcities.response;

import lombok.Getter;
import lombok.Setter;
/**
 * Author: Badreddine TIRGANI
 */
@Getter
@Setter
public class JwtResponse {

    private String token;

    public JwtResponse(String jwt) {
        this.token=jwt;
    }
}