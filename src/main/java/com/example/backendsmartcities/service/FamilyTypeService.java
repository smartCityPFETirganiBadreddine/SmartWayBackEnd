package com.example.backendsmartcities.service;

import com.example.backendsmartcities.dto.FamilyTypeDto;
import com.example.backendsmartcities.dto.ReclamationDto;
import com.example.backendsmartcities.entity.Branch;
import com.example.backendsmartcities.entity.FamilyType;
import com.example.backendsmartcities.entity.Reclamation;
import com.example.backendsmartcities.enums.ReclamationStatus;
import com.example.backendsmartcities.repository.BranchRepository;
import com.example.backendsmartcities.repository.FamilyTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * Author: Badreddine TIRGANI
 */
@Service
public class FamilyTypeService implements BaseService<FamilyTypeDto> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FamilyTypeRepository familyTypeRepository;
    @Autowired
    private BranchRepository branchRepository;


    @Override
    public FamilyTypeDto save(FamilyTypeDto family) throws Exception {
        FamilyType savedFamily;
        if (family.getId() != null) {
            Optional<FamilyType> optionalFamily = familyTypeRepository.findById(family.getId());
            if (optionalFamily.isPresent()) {
                FamilyType existingFamily = optionalFamily.get();
                existingFamily.setCode(family.getCode());
                existingFamily.setName(family.getName());
                savedFamily = existingFamily;
            } else {
                throw new EntityNotFoundException("FamilyType not found with id " + family.getId());
            }
        } else {
            FamilyType newFamily = modelMapper.map(family, FamilyType.class);
            savedFamily = newFamily;
        }
        if (family.getBranch() != null) {
            Long branchId = family.getBranch().getId();
            Branch branch = branchRepository.findById(branchId)
                    .orElseThrow(() -> new Exception("Branch not found with id " + branchId));
            savedFamily.setBranch(branch);
        }
        savedFamily = familyTypeRepository.save(savedFamily);
        return modelMapper.map(savedFamily, FamilyTypeDto.class);
    }
    @Override
    public FamilyTypeDto update(long id, FamilyTypeDto familyTypeRequest) throws Exception {
        FamilyType familyType = familyTypeRepository.findById(id).orElseThrow(() -> new Exception("No user registered with id " + id));
        if (familyTypeRequest != null) {
            BeanUtils.copyProperties(familyTypeRequest, familyType, Stream.of(new BeanWrapperImpl(familyTypeRequest).getPropertyDescriptors()).map(FeatureDescriptor::getName).filter(propertyName -> new BeanWrapperImpl(familyTypeRequest).getPropertyValue(propertyName) == null).toArray(String[]::new));

        }
        FamilyType u1 = familyTypeRepository.save(familyType);
        return modelMapper.map(u1, FamilyTypeDto.class);
    }

    @Override
    public void softDelete(Long id) {
        Optional<FamilyType> familyType = familyTypeRepository.findById(id);
        if (familyType.isPresent()) {
            FamilyType existingFamilyType = familyType.get();

                //existingFamilyType.setDeleteAt(new Date());
                existingFamilyType.setActive(false);
                existingFamilyType.setDeleted(true);
                familyTypeRepository.save(existingFamilyType);


        } else {
            throw new EntityNotFoundException("Family Type with id " + id + " not found.");
        }
    }


    @Override
    public Optional<FamilyTypeDto> findById(Long id) {
        Optional<FamilyType> sc = familyTypeRepository.findById(id);
        return sc.map(user -> modelMapper.map(user, FamilyTypeDto.class));
    }

    @Override
    public List<FamilyTypeDto> getAll() {

        List<FamilyType> familyTypes = familyTypeRepository.findByDeletedFalse();
        return familyTypes.stream()
                .map(familyType -> modelMapper.map(familyType, FamilyTypeDto.class))
                .collect(Collectors.toList());
    }

    public List<FamilyTypeDto> getAllByBranch(String branch) {
        Branch branch1 = branchRepository.findByName(branch);
        List<FamilyType> familyTypes = familyTypeRepository.findByDeletedFalseAndBranch(branch1);
        return familyTypes.stream()
                .map(familyType -> modelMapper.map(familyType, FamilyTypeDto.class))
                .collect(Collectors.toList());
    }
}
