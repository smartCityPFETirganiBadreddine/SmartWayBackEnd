package com.example.backendsmartcities.security.jwt;

import com.example.backendsmartcities.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
/**
 * Author: Badreddine TIRGANI
 */
@Getter
@Setter
public class RefreshToken {
    private String token;
    private LocalDateTime expiryDate;
    private User user;


}

