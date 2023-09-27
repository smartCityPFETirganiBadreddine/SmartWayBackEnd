package com.example.backendsmartcities.dto;

import com.example.backendsmartcities.entity.Intervention;
import com.example.backendsmartcities.entity.Type;
import lombok.Getter;
import lombok.Setter;
/**
 * Author: Badreddine TIRGANI
 */
@Getter
@Setter
public class InterventionFlowDto {
    private Long id;
    private String mobileId;
    private double trashQte;
    private String comment;
    private double quantity;


    private Intervention intervention;


    private Type type;
}
