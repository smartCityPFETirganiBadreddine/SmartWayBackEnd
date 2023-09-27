package com.example.backendsmartcities.constroller;

import com.example.backendsmartcities.dto.ClientInfoDto;
import com.example.backendsmartcities.dto.ReclamationDto;
import com.example.backendsmartcities.entity.ClientInfo;
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
@RequestMapping("/api/reclamation")
public class ReclamationController {


    private final ReclamationService reclamationService;

    public ReclamationController(ReclamationService reclamationService) {
        this.reclamationService = reclamationService;
    }

    @PostMapping
    public ResponseEntity<ReclamationDto> createReclamation(@RequestBody ReclamationDto reclamationDto) {
        try {
            ReclamationDto createdReclamation = reclamationService.save(reclamationDto);
            return new ResponseEntity<>(createdReclamation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ReclamationDto> updateReclamation(@PathVariable Long id, @RequestBody ReclamationDto reclamationDto) {
        try {
            ReclamationDto updatedReclamation = reclamationService.update(id, reclamationDto);
            return new ResponseEntity<>(updatedReclamation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/branch/{id}")
    public ResponseEntity<List<ReclamationDto>> getAllReclamations(@PathVariable Long id) {
        List<ReclamationDto> reclamations = reclamationService.getAllByBranchId(id);
        if (reclamations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reclamations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReclamationDto> getReclamationById(@PathVariable Long id) {
        Optional<ReclamationDto> reclamation = reclamationService.findById(id);
        return reclamation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/contrats")
    public ResponseEntity<List<ClientInfoDto>> getAllClientInfo() {
        List<ClientInfoDto> contrats = reclamationService.getAllClientInfo();
        if (contrats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(contrats, HttpStatus.OK);
    }
    @GetMapping("getReclamationsByTeamId/{id}")
    public ResponseEntity<List<ReclamationDto>> getReclamationsByTeamId(@PathVariable Long id) {
        List<ReclamationDto> reclamations = reclamationService.getReclamationsByTeamId(id);
        if (reclamations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reclamations, HttpStatus.OK);
    }

    @GetMapping("getContratByNumPolice/{n}")
    public ResponseEntity<ReclamationDto> getContratByNumPolice(@PathVariable String  n) {
        ClientInfoDto reclamations = reclamationService.getClientInfoByNumPolice(n);
        if (reclamations==null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(reclamations, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReclamation(@PathVariable Long id) {
        try {
            reclamationService.softDelete(id);
            return new ResponseEntity<>("Reclamation with id " + id + " has been deleted.", HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Reclamation must not be in EN_COURS status to be deleted.", HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Reclamation with id " + id + " not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the reclamation.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}