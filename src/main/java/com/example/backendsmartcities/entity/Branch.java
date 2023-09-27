package com.example.backendsmartcities.entity;

import lombok.*;

import javax.persistence.*;
/**
 * Author: Badreddine TIRGANI
 */
@Table(name = "branch")
@Entity(name = "Branch")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
//@JsonIdentityInfo(scope = Branch.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Branch extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

}
