package com.example.backendsmartcities.entity;

import com.example.backendsmartcities.enums.ERole;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
/**
 * Author: Badreddine TIRGANI
 */
@Entity
@Table(name = "roles")

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@JsonIdentityInfo(scope = Role.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Role extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ERole name;
}