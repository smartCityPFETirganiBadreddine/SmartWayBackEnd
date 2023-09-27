package com.example.backendsmartcities.dto;

import com.example.backendsmartcities.entity.Intervention;
import lombok.Getter;
import lombok.Setter;
/**
 * Author: Badreddine TIRGANI
 */

@Getter
@Setter
public class ImageDto {
    private Long id;
    private String path;
    private String name;


    private Intervention intervention;
}
