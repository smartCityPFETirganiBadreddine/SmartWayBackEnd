package com.example.backendsmartcities.dto;

import com.example.backendsmartcities.entity.Branch;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FamilyTypeDto {
    private Long id;
    private int code;
    private String name;

    @JsonIgnoreProperties(value = {"description", "createdAt", "updatedAt"})
    private BranchDto branch;

    public BranchDto getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        BranchDto branchDto = new BranchDto();
        branchDto.setId(branch.getId());
        branchDto.setName(branch.getName());
        this.branch = branchDto;
    }
}
