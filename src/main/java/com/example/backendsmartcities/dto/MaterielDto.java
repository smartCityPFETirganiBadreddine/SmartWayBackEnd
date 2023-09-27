package com.example.backendsmartcities.dto;

import com.example.backendsmartcities.entity.Branch;
import com.example.backendsmartcities.entity.MaterielFlow;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Author: Badreddine TIRGANI
 */
@Getter
@Setter
public class MaterielDto {
    private Long id;
    private String name;
    private  double quantity;
    private Date lastInputDate;
    private Date lastOutputDate;
    private String unit;
    private String movement;
    private String marque;

    private List<MaterielFlow> materielFlows = new ArrayList<>();

    private Branch branch;
    private boolean isActive;
}
