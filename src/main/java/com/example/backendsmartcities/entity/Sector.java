package com.example.backendsmartcities.entity;

import lombok.*;

import javax.persistence.*;
/**
 * Author: Badreddine TIRGANI
 */
@Table(name = "sector")
@Entity(name = "Sector")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
//@JsonIdentityInfo(scope = Sector.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")


public class Sector extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
