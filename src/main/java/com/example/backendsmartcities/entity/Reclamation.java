package com.example.backendsmartcities.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: Badreddine TIRGANI
 */
@Table(name = "reclamation")
@Entity(name = "Reclamation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(scope = Reclamation.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Reclamation extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private String num_Contrat;
    private String reclamationSource;
    private String typeClient;
    private String email;
    private String reclamationNumber;
    private int source;
    private LocalDateTime reclamationDate;
    private int priority;
    private LocalDateTime editionDateTime;
    private String objectReclamation;
    private String adresseReclamation;
    private String telportable;
    private String fax;
    private String clientFullName;

    private String reclamationType;
    @ManyToOne
    private ClientInfo clientInfo;
    @ManyToOne
    private Type type;
    @ManyToOne
    private FamilyType familyType;
    @ManyToOne
    private Branch branch;
    private boolean typeReclamation;
    private String secteur;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String observation;

    @ManyToOne
    private Status  status;




}
