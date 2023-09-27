package com.example.backendsmartcities.dto;

import com.example.backendsmartcities.entity.FamilyType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;
/**
 * Author: Badreddine TIRGANI
 */
@Getter
@Setter
public class TypeDto {

    private Long id;
    private String libelle;
    private FamilyTypeDto familyType;
}
