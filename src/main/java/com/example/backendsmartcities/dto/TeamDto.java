package com.example.backendsmartcities.dto;

import com.example.backendsmartcities.entity.Branch;
import com.example.backendsmartcities.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
/**
 * Author: Badreddine TIRGANI
 */
@Getter
@Setter
public class TeamDto {
    private Long id;
    private String teamName;
    private String specialite;
    private String latitude;
    private String longitude;
    private Date lastLocationDate;
    private int nVehicule;
    private BranchDto branch;
    private UserDto superviseur;
    private UserDto chefEquipe;
    private List<UserDto> members;
    private UserDto editeur;
    private boolean isActive;
    private String fullNameEditeur;
}
