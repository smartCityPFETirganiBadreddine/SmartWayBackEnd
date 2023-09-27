package com.example.backendsmartcities.constroller;

import com.example.backendsmartcities.dto.MaterielDto;
import com.example.backendsmartcities.exception.DuplicateEntityException;
import com.example.backendsmartcities.exception.ErrorResponse;
import com.example.backendsmartcities.exception.MaterielServiceException;
import com.example.backendsmartcities.service.MaterielService;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/materiel")
public class MaterielController {

    private final MaterielService materielService;

    public MaterielController(MaterielService materielService) {
        this.materielService = materielService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MaterielDto materielDto) {
        try {
            MaterielDto createdMateriel = materielService.save(materielDto);
            return new ResponseEntity<>(createdMateriel, HttpStatus.CREATED);
        } catch (DuplicateEntityException e) {
            MaterielDto errorMateriel = new MaterielDto();
            errorMateriel.setName(e.getMessage());
            return new ResponseEntity<>(errorMateriel, HttpStatus.BAD_REQUEST);
        } catch (MaterielServiceException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterielDto> update(@PathVariable Long id, @RequestBody MaterielDto materielDto) {
        try {
            MaterielDto updatedMateriel = materielService.update(id, materielDto);
            return ResponseEntity.ok(updatedMateriel);
        } catch (ConfigDataResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<MaterielDto>> getAllTeams() {
        List<MaterielDto> materielDtos = materielService.getAll();
        if (materielDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(materielDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterielDto> getTeamById(@PathVariable Long id) {
        Optional<MaterielDto> materielDto = materielService.findById(id);
        return materielDto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long id) {
        try {
            materielService.softDelete(id);
            return new ResponseEntity<>("Materiel with id " + id + " has been deleted.", HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Materiel must not be in EN_COURS status to be deleted.", HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Materiel with id " + id + " not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the team.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
