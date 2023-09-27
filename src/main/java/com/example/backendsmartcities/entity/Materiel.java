package com.example.backendsmartcities.entity;

import lombok.*;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Author: Badreddine TIRGANI
 */
@Table(name = "materiel")
@Entity(name = "Materiel")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@SQLDelete(sql = "update Materiel set deleted = true where id = ?")
@NamedQuery(name = "findMaterielById", query = "select m from Materiel m where m.id = ?1 and m.deleted = false")
@NamedQuery(name = "findAllMateriel", query = "select m from Materiel m where   m.deleted = false")
@Loader(namedQuery = "findAllMateriel")
public class Materiel extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private  double quantity;
    private Date lastInputDate;
    private Date lastOutputDate;
    private String unit;
    private String movement;
    private String marque;
    @OneToMany(mappedBy = "materiel")
    private List<MaterielFlow> materielFlows = new ArrayList<>();
    @ManyToOne
    private Branch branch;
    private boolean deleted;
    private Date deletedAt;

    // Constructors, getters and setters
}