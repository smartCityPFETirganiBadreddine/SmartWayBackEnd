package com.example.backendsmartcities.dto;

import com.example.backendsmartcities.enums.ERole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
/**
 * Author: Badreddine TIRGANI
 */
@Getter
@Setter
public class RoleDto {

    @Enumerated(EnumType.STRING)
    private ERole name;


}
