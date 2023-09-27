package com.example.backendsmartcities.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
/**
 * Author: Badreddine TIRGANI
 */
@Data
@Getter
@Setter
public class LoginDto {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;
}
