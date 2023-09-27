package com.example.backendsmartcities.constroller;

import com.example.backendsmartcities.dto.TypeDto;
import com.example.backendsmartcities.service.TypeService;
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
@RequestMapping("/api/type")
public class TypeController {
    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    // Create
    @PostMapping
    public ResponseEntity<TypeDto> createType(@RequestBody TypeDto typeDto) {
        try {
            TypeDto t = typeService.save(typeDto);
            return new ResponseEntity<>(t, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<TypeDto>> getAllTypes() {
        List<TypeDto> types = typeService.getAll();
        return ResponseEntity.ok(types);
    }
    @GetMapping("/famille/{id}")
    public ResponseEntity<List<TypeDto>> getAllTypeByFamille(@PathVariable Long id) {
        List<TypeDto> types = typeService.getAllByFamille(id);
        return ResponseEntity.ok(types);
    }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<TypeDto> getType(@PathVariable Long id) {
        Optional<TypeDto> typeDto = typeService.findById(id);
        return typeDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<TypeDto> updateType(@PathVariable Long id, @RequestBody TypeDto typeDto) {
        typeDto.setId(id);
        try {
            TypeDto t = typeService.save(typeDto);
            return new ResponseEntity<>(t, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        typeService.softDelete(id);
        return ResponseEntity.ok().build();
    }
}