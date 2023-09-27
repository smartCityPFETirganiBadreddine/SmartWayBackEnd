package com.example.backendsmartcities.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
/**
 * Author: Badreddine TIRGANI
 */
@Table(name = "interventionFlow")
@Entity(name = "InterventionFlow")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(scope = InterventionFlow.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class InterventionFlow extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mobileId;
    private double trashQte;
    private String comment;
    private double quantity;

    @ManyToOne
    private Intervention intervention;

    @ManyToOne
    private Type type;

}
