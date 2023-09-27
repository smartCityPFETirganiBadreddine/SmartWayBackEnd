package com.example.backendsmartcities.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Badreddine TIRGANI
 */
@Table(name = "intervention")
@Entity(name = "Intervention")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "reclamation")
@JsonIdentityInfo(scope = Intervention.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Intervention extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime interventionDateTime;
    private  String  comment;
    private LocalDateTime editionDateTime;
    private int numberMaterial;
    private double arrivalDelay;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime planningDateTime;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime pickupDateTime;
    private String Adresse;
    private String longitude;
    private String latitude;

    @OneToMany(mappedBy = "intervention")
    private List<MaterielFlow> materielFlows = new ArrayList<>();

    @JsonIgnoreProperties
    @ManyToOne
    private Reclamation reclamation;

    @JsonIgnoreProperties
    @ManyToOne
    private Team team;
    @ManyToOne
    private Type type;
    @ManyToOne
    private FamilyType familyType;
    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Status  status;
}
