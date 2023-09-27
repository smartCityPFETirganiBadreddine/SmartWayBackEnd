package com.example.backendsmartcities.entity;

import com.example.backendsmartcities.enums.EStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

/**
 * Author: Badreddine TIRGANI
 */
@Table(name = "status")
@Entity(name = "Status")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Status extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @Column(name = "statu") // Specify column name
    private String statu;

   // @ManyToOne
   // @JoinColumn(name = "reclamation_id", nullable = false)
    @JsonIgnoreProperties
    @ManyToOne
    private Reclamation reclamation;

    @JsonIgnoreProperties
    @ManyToOne
    private Intervention intervention;

    private boolean isReclamation;

    @JsonIgnoreProperties
    @ManyToOne
    private Team team;

    public Status(EStatus status, Reclamation reclamation, boolean isReclamation) {
        this.statu = status.name();
        this.reclamation = reclamation;
        this.isReclamation = isReclamation;
    }

    public Status(EStatus status, Intervention intervention, boolean isReclamation) {
        this.statu = status.name();
        this.intervention = intervention;
        this.isReclamation = isReclamation;
    }
}
