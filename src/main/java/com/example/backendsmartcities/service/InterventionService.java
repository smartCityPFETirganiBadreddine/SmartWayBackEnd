package com.example.backendsmartcities.service;

import com.example.backendsmartcities.dto.InterventionDto;
import com.example.backendsmartcities.dto.ReclamationDto;
import com.example.backendsmartcities.dto.UserDto;
import com.example.backendsmartcities.entity.Intervention;
import com.example.backendsmartcities.entity.Reclamation;
import com.example.backendsmartcities.entity.Status;
import com.example.backendsmartcities.enums.EStatus;
import com.example.backendsmartcities.repository.ContratsRepository;
import com.example.backendsmartcities.repository.InterventionRepository;
import com.example.backendsmartcities.repository.ReclamationRepository;
import com.example.backendsmartcities.repository.StatusRepository;
import com.google.protobuf.Empty;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Author: Badreddine TIRGANI
 */
@Service
public class InterventionService implements BaseService<InterventionDto> {

    @Autowired
    private ReclamationRepository reclamationRepository;
    @Autowired
    private ContratsRepository contratsRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private InterventionRepository interventionRepository;

    @Override
    public InterventionDto save(InterventionDto interventionDto) throws Exception {
        Intervention intervention = modelMapper.map(interventionDto, Intervention.class);
        intervention.setInterventionDateTime(LocalDateTime.now());
        intervention.setEditionDateTime(LocalDateTime.now());
        Intervention createdIntervention = interventionRepository.save(intervention);
        Status status = new Status(EStatus.CREE, createdIntervention, false);
        statusRepository.save(status);
        //createdIntervention.setStatus(status);
        interventionRepository.save(createdIntervention);
        return modelMapper.map(createdIntervention, InterventionDto.class);
    }


    @Override
    public InterventionDto update(long id, InterventionDto obj) throws Exception {
        return null;
    }

    @Override
    public void softDelete(Long id) {
        Optional<Intervention> intervention = interventionRepository.findById(id);
        if (intervention.isPresent()) {
            Intervention exictingIntervention = intervention.get();
            //   if (existingReclamation.getStatus() != ReclamationStatus.EN_COURS) {
            //existingReclamation.setDeleteAt(new Date());
            exictingIntervention.setActive(false);
            exictingIntervention.setDeleted(true);
            interventionRepository.save(exictingIntervention);


        } else {
            throw new EntityNotFoundException("Intervention with id " + id + " not found.");
        }
    }

    @Override
    public Optional<InterventionDto> findById(Long id) {
        Optional<Intervention> optionalIntervention = interventionRepository.findById(id);
        return optionalIntervention.map(intervention -> modelMapper.map(intervention, InterventionDto.class));

    }
    public Optional<Intervention> findByIdj(Long id) {
        Optional<Intervention> optionalIntervention = interventionRepository.findById(id);
        //return optionalIntervention.map(intervention -> modelMapper.map(intervention, InterventionDto.class));
        return  optionalIntervention;

    }
    @Override
    public List<InterventionDto> getAll() {
        List<Intervention> interventions = interventionRepository.findByDeletedFalse();
         return interventions.stream()
                 .map(intervention -> modelMapper.map(intervention, InterventionDto.class))
                 .collect(Collectors.toList());
        //return interventions;
    }


    public List<Intervention> getAlli() {
        List<Intervention> interventions = interventionRepository.findByDeletedFalse();
        return interventions.stream()
               // .map(intervention -> modelMapper.map(intervention, InterventionDto.class))
                .collect(Collectors.toList());

    }

    public List<Intervention> getAllByBranchId(Long branchId) {
        List<Intervention> interventions = interventionRepository.findByDeletedFalseAndBranchId(branchId);
        return interventions.stream()
               // .map(intervention -> modelMapper.map(intervention, InterventionDto.class))
                .collect(Collectors.toList());
    }

    public List<Intervention> getAllByReclamationId(Optional<ReclamationDto> reclamationDtoOptional) {
        // Use isPresent() instead of isEmpty()
        if (reclamationDtoOptional.isEmpty()) {
            return Collections.emptyList();
        }

        ReclamationDto reclamationDto = reclamationDtoOptional.get();
        Reclamation reclamation = modelMapper.map(reclamationDto, Reclamation.class);
        List<Intervention> interventions = interventionRepository.findByReclamation(reclamation);

        // Use method reference instead of a lambda expression for better readability
        //return interventions.stream()
        //        .map(intervention -> modelMapper.map(intervention, InterventionDto.class))
        //        .collect(Collectors.toList());
        return interventions.stream()
                .collect(Collectors.toList());

    }




    private Reclamation convertToReclamation(ReclamationDto reclamationDto) {
        return modelMapper.map(reclamationDto, Reclamation.class);
    }


}
