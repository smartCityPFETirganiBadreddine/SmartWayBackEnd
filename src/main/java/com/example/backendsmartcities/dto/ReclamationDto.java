package com.example.backendsmartcities.dto;

import com.example.backendsmartcities.entity.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
/**
 * Author: Badreddine TIRGANI
 */
@Getter
@Setter
public class ReclamationDto {
    // Define the Casablanca time zone
    ZoneId casablancaZone = ZoneId.of("Africa/Casablanca");

    private Long id;
    private String category;
    private String typeClient;
    private String reclamationNumber;
    private int source;
    private LocalDateTime reclamationDate;
    private int priority;
    private LocalDateTime editionDateTime;
    private String objectReclamation;
    private String adresseReclamation;
    private String telportable;
    private String fax;
    private String reclamationType;
    private String num_Contrat;
    private ClientInfoDto clientInfo;
    private String email;
    private TypeDto type;

    private FamilyTypeDto familyType;

    private BranchDto branch;
    private boolean typeReclamation;
    private String secteur;
    private String observation;
    private String reclamationSource;
    private String clientFullName;
    private StatusDto status;
    private LocalDateTime updatedAt;
    private boolean isActive;
    private boolean deleted;
    private Date deleteAt;
}
