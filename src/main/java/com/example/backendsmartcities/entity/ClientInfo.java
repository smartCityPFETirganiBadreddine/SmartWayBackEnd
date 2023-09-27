package com.example.backendsmartcities.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
/**
 * Author: Badreddine TIRGANI
 */
@Table(name = "clientInfo")
@Entity(name = "ClientInfo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(scope = ClientInfo.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class ClientInfo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Sector sector;
    private String tourNumber;
    private String clientName;
    private String phone;
    private String address;
    //TODO add getClientInfoByNum_police
    private String numPolice;
    private String codeLocalite;
    private String numSecteur;
    private String numBloc;
    private String numOrdre;
    private String refCompteur;
    private String clientCin;
    private String police;

}
