package com.example.backendsmartcities.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
/**
 * Author: Badreddine TIRGANI
 */
@Table(name = "familyType")
@Entity(name = "FamilyType")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(scope = FamilyType.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class FamilyType extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int code;
    private String name;

    @ManyToOne
    private Branch branch;
}
