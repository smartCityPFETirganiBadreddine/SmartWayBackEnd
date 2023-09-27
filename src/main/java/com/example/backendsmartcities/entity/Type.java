package com.example.backendsmartcities.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
/**
 * Author: Badreddine TIRGANI
 */
@Table(name = "type")
@Entity(name = "Type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(scope = Type.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Type extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;

    @ManyToOne

    private FamilyType familyType;
}
