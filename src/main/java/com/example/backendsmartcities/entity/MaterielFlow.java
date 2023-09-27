package com.example.backendsmartcities.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
/**
 * Author: Badreddine TIRGANI
 */
@Table(name = "materielFlow")
@Entity(name = "MaterielFlow")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(scope = MaterielFlow.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class MaterielFlow extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Intervention intervention;

    @ManyToOne
    private Materiel materiel;

    private double quantity;

    private String type;

    @Temporal(TemporalType.DATE)
    private Date actionDate;

    private String motif;

}
