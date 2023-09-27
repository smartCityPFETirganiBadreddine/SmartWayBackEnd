package com.example.backendsmartcities.dto;

import com.example.backendsmartcities.entity.Sector;
import lombok.Getter;
import lombok.Setter;
/**
 * Author: Badreddine TIRGANI
 */

@Getter
@Setter
public class ClientInfoDto {
    private Long id;
    private Sector sector;
    private String tourNumber;
    private String clientName;
    private String phone;
    private String address;
    private String numPolice;
    private String codeLocalite;
    private String numSecteur;
    private String numBloc;
    private String numOrdre;
    private String refCompteur;
    private String clientCin;
    private String police;
}
