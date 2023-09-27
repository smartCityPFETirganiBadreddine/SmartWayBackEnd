package com.example.backendsmartcities.dto;

import com.example.backendsmartcities.entity.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: Badreddine TIRGANI
 */
@Getter
@Setter
public class NotificationDto {
    private Long id;
    private String title;
    private String description;
    private boolean seen;

    private User user;
}
