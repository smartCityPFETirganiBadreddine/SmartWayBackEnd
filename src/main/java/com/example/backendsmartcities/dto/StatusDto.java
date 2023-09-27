package com.example.backendsmartcities.dto;

import com.example.backendsmartcities.enums.EStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Author: Badreddine Tirgani
 * Date: 20/06/2023
 */
@Getter
@Setter
public class StatusDto {
    private Long id;
    private EStatus status;
    //private InterventionDto intervention;
    private boolean isActive;
    private LocalDateTime createAt;
    private String statu;

   }