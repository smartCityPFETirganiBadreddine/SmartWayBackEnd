package com.example.backendsmartcities.constroller;

import com.example.backendsmartcities.dto.InterventionDto;
import com.example.backendsmartcities.dto.ReclamationDto;
import com.example.backendsmartcities.entity.Intervention;
import com.example.backendsmartcities.service.InterventionService;
import com.example.backendsmartcities.service.ReclamationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Author: Badreddine TIRGANI
 */
@CrossOrigin(value = "*", exposedHeaders = "")
@RestController
@RequestMapping("/api/intervention")
public class InterventionController {


    private final InterventionService interventionService;
    private final ReclamationService  reclamationService;


    public InterventionController(InterventionService interventionService, ReclamationService reclamationService) {
        this.interventionService = interventionService;
        this.reclamationService = reclamationService;
    }

    @PostMapping
    public ResponseEntity<InterventionDto> createIntervention(@RequestBody InterventionDto interventionDto) {
        try {
            InterventionDto createdIntervention = interventionService.save(interventionDto);
            return new ResponseEntity<>(createdIntervention, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<InterventionDto> updateIntervention(@PathVariable Long id, @RequestBody InterventionDto interventionDto) {
        try {
            InterventionDto updatedIntervention = interventionService.update(id, interventionDto);
            return new ResponseEntity<>(updatedIntervention, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/branch/{id}")
    public ResponseEntity<List<Intervention>> getAllInterventions(@PathVariable Long id) {
        List<Intervention> interventions = interventionService.getAllByBranchId(id);
        if (interventions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(interventions, HttpStatus.OK);
    }

    @GetMapping("/byReclamation/{id}")
    public ResponseEntity<List<Intervention>> getAllInterventionsByReclamationId(@PathVariable Long id) {
        Optional<ReclamationDto> reclamation = reclamationService.findById(id);
        List<Intervention> interventions = interventionService.getAllByReclamationId(reclamation);
        if (interventions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(interventions, HttpStatus.OK);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Intervention> getInterventionById(@PathVariable Long id) {
        Optional<Intervention> intervention = interventionService.findByIdj(id);
        return intervention.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping
    public ResponseEntity<List<Intervention>> getAllTeams() {
        List<Intervention> teamDtos = interventionService.getAlli();
        if (teamDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teamDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIntervention(@PathVariable Long id) {
        try {
            interventionService.softDelete(id);
            return new ResponseEntity<>("Intervention with id " + id + " has been deleted.", HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Intervention must not be in EN_COURS status to be deleted.", HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Intervention with id " + id + " not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the Intervention.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}