package com.example.backendsmartcities.dto;

import com.example.backendsmartcities.entity.Branch;
import com.example.backendsmartcities.entity.Team;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
/**
 * Author: Badreddine TIRGANI
 */
@Getter
@Setter
public class UserDto {

    private Long id;
    private String userName;
    private String email;
    private boolean enabled;
    private String password;
    private Date lastLogin;
    private List<RoleDto> roles ;
    private String firstName;
    private String lastName;
    private boolean locked;
    private String phone;
    private Branch branch;
    private Team team;
    private String matricule;
    private boolean chargeDeTravaux;
    private String cin;
}
