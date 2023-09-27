package com.example.backendsmartcities.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
/**
 * Author: Badreddine TIRGANI
 */
@Table(name = "team")
@Entity(name = "Team")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
//@JsonIdentityInfo(scope = Team.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Team extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    private String specialite;
    private String latitude;
    private String longitude;
    private Date lastLocationDate;
    private int nVehicule;

    @ManyToOne
    private Branch branch;
    @OneToOne
    //@JsonIgnoreProperties("team")
    private User superviseur;
    @OneToOne
    //@JsonIgnoreProperties("team")
    private User chefEquipe;

    @ManyToMany
    @JoinTable(
            name = "team_user",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
   //@JsonIgnoreProperties("team")
    private List<User> members;

}
