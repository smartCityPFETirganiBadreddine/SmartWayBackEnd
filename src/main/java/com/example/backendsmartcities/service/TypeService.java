package com.example.backendsmartcities.service;

import com.example.backendsmartcities.dto.TypeDto;
import com.example.backendsmartcities.entity.FamilyType;
import com.example.backendsmartcities.entity.Type;
import com.example.backendsmartcities.repository.FamilyTypeRepository;
import com.example.backendsmartcities.repository.TypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Author: Badreddine TIRGANI
 */
@Service
public class TypeService implements BaseService<TypeDto> {

    private final ModelMapper modelMapper;

    private final TypeRepository typeRepository;
    private final FamilyTypeRepository familyTypeRepository;

    @Autowired
    public TypeService(ModelMapper modelMapper, TypeRepository typeRepository, FamilyTypeRepository familyTypeRepository) {
        this.modelMapper = modelMapper;
        this.typeRepository = typeRepository;
        this.familyTypeRepository = familyTypeRepository;
    }

    @Override
    public TypeDto save(TypeDto type) throws Exception {
        Optional<FamilyType> familyType = familyTypeRepository.findById(type.getFamilyType().getId());



        if (!familyType.isPresent()) {
            throw new Exception("Family type with id " + type.getFamilyType().getId() + " not found");
        }

        Type savedType = modelMapper.map(type, Type.class);
        savedType.setFamilyType(familyType.get());
        savedType = typeRepository.save(savedType);

        return modelMapper.map(savedType, TypeDto.class);
    }

    @Override
    public TypeDto update(long id, TypeDto obj) throws Exception {
        Optional<Type> existingType = typeRepository.findById(id);
        if (existingType.isEmpty()) {
            throw new Exception("Type not found with id " + id);
        }
        Type type = modelMapper.map(obj, Type.class);
        type.setId(id);
        type = typeRepository.save(type);
        return modelMapper.map(type, TypeDto.class);
    }

    @Override
    public void softDelete(Long id) {
        Optional<Type> existingType = typeRepository.findById(id);
        if (existingType.isPresent()) {
            Type type = existingType.get();
            type.setDeleted(true);
            typeRepository.save(type);
        }
    }

    @Override
    public Optional<TypeDto> findById(Long id) {
        Optional<Type> type = typeRepository.findById(id);
        return type.map(t -> modelMapper.map(t, TypeDto.class));
    }

    @Override
    public List<TypeDto> getAll() {
        return typeRepository.findAllByDeletedFalse()
                .stream()
                .map(type -> modelMapper.map(type, TypeDto.class))
                .collect(Collectors.toList());
    }

    public List<TypeDto> getAllByFamille(Long id) {
        FamilyType familyType = familyTypeRepository.getById(id);
        return typeRepository.findAllByDeletedFalseAndFamilyType(familyType)
                .stream()
                .map(type -> modelMapper.map(type, TypeDto.class))
                .collect(Collectors.toList());
    }
}