package com.example.backendsmartcities.constroller;

import com.example.backendsmartcities.dto.BranchDto;
import com.example.backendsmartcities.dto.FamilyTypeDto;
import com.example.backendsmartcities.dto.TypeDto;
import com.example.backendsmartcities.entity.Branch;
import com.example.backendsmartcities.service.BranchService;
import com.example.backendsmartcities.service.FamilyTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
@CrossOrigin(value = "*", exposedHeaders = "")
@RestController
@RequestMapping("/api/familyType")
public class FamilyTypeController {
    private final FamilyTypeService familyTypeService;
    private final BranchService branchService;


    public FamilyTypeController(FamilyTypeService familyTypeService, BranchService branchService) {
        this.familyTypeService = familyTypeService;
        this.branchService = branchService;
    }

    // Create
    @PostMapping
    public ResponseEntity<FamilyTypeDto> createFamilyType(@RequestBody FamilyTypeDto familyTypeDto) {
        try {
            // Check if the corresponding Branch object exists
            Long branchId = familyTypeDto.getBranch().getId();
            Optional<BranchDto> branch = branchService.findById(branchId);
            if (branch.isEmpty()) {
                throw new Exception("Branch not found with id " + branchId);
            }

            FamilyTypeDto f = familyTypeService.save(familyTypeDto);
            return new ResponseEntity<>(f, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Read
    @GetMapping("/{id}")
    public ResponseEntity<FamilyTypeDto> getFamilyType(@PathVariable Long id) {
        Optional<FamilyTypeDto> familyTypeDto = familyTypeService.findById(id);
        return familyTypeDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<FamilyTypeDto> updateFamilyType(@PathVariable Long id, @RequestBody FamilyTypeDto familyTypeDto) {
        familyTypeDto.setId(id);
        try {
            FamilyTypeDto f = familyTypeService.save(familyTypeDto);
            return new ResponseEntity<>(f, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFamilyType(@PathVariable Long id) {
        familyTypeService.softDelete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<FamilyTypeDto>> getAllTypes() {
        List<FamilyTypeDto> types = familyTypeService.getAll();
        return ResponseEntity.ok(types);
    }
    @GetMapping("/getAllByBranch/{branch}")
    public ResponseEntity<List<FamilyTypeDto>> getAllByBranch(@PathVariable String  branch) {
        List<FamilyTypeDto> types = familyTypeService.getAllByBranch(branch);
        return ResponseEntity.ok(types);
    }
}