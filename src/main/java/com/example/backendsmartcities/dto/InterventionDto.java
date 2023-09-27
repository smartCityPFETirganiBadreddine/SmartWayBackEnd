package com.example.backendsmartcities.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Author: Badreddine TIRGANI
 */
@Getter
@Setter
public class InterventionDto {
    private Long id;
    private LocalDateTime interventionDateTime;
    private  String  comment;
    private LocalDateTime editionDateTime;
    private int numberMaterial;
    private double arrivalDelay;
    private LocalDateTime planningDateTime;
    private LocalDateTime pickupDateTime;
    private String Adresse;
    private String longitude;
    private String latitude;

    private List<MaterielFlowDto> materielFlows = new ArrayList<>();

    private ReclamationDto reclamation;

    private TeamDto team;
    private StatusDto status;

    private TypeDto type;

    private FamilyTypeDto familyType;

    private BranchDto branch;

}
