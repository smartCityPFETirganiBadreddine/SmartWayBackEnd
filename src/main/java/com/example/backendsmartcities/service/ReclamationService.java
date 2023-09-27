package com.example.backendsmartcities.service;

import com.example.backendsmartcities.dto.ClientInfoDto;
import com.example.backendsmartcities.dto.ReclamationDto;
import com.example.backendsmartcities.entity.ClientInfo;
import com.example.backendsmartcities.entity.Reclamation;
import com.example.backendsmartcities.entity.Status;
import com.example.backendsmartcities.enums.EStatus;
import com.example.backendsmartcities.enums.ReclamationStatus;
import com.example.backendsmartcities.repository.ContratsRepository;
import com.example.backendsmartcities.repository.ReclamationRepository;
import com.example.backendsmartcities.repository.StatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Author: Badreddine TIRGANI
 */
@Service
public class ReclamationService implements BaseService<ReclamationDto> {

    @Autowired
    private ReclamationRepository reclamationRepository;
    @Autowired
    private ContratsRepository contratsRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReclamationDto save(ReclamationDto reclamationDto) throws Exception {
        Reclamation reclamation  =modelMapper.map(reclamationDto, Reclamation.class);
        reclamation.setReclamationDate(LocalDateTime.now() );
        reclamation.setEditionDateTime(LocalDateTime.now());
        Reclamation createdReclamation = reclamationRepository.save(reclamation);
        Status status = new Status(EStatus.CREE,createdReclamation,true);
        statusRepository.save(status);
        createdReclamation.setStatus(status);
        reclamationRepository.save(createdReclamation);
        return modelMapper.map(createdReclamation, ReclamationDto.class);
    }

    @Override
    public ReclamationDto update(long id, ReclamationDto reclamationDto) throws Exception {
        Optional<Reclamation> reclamationOptional = reclamationRepository.findById(id);
        if (reclamationOptional.isPresent()) {
            Reclamation existingReclamation = reclamationOptional.get();
            Reclamation updatedReclamation = modelMapper.map(reclamationDto, Reclamation.class);
            updatedReclamation.setId(existingReclamation.getId());
         //   updatedReclamation.setStatus(existingReclamation.getStatus());  // Preserve status
            updatedReclamation.setReclamationDate(LocalDateTime.now() );
            updatedReclamation.setEditionDateTime(LocalDateTime.now());
            Status status = new Status(EStatus.CREE,updatedReclamation,true);
            statusRepository.save(status);
            updatedReclamation.setStatus(status);
            Reclamation savedReclamation = reclamationRepository.save(updatedReclamation);

            return modelMapper.map(savedReclamation, ReclamationDto.class);
        }
        throw new Exception("Reclamation not found with id " + id);
    }



    @Override
    public Optional<ReclamationDto> findById(Long id) {
        Optional<Reclamation> reclamationOptional = reclamationRepository.findById(id);
        return reclamationOptional.map(reclamation -> modelMapper.map(reclamation, ReclamationDto.class));
    }

    @Override
    public List<ReclamationDto> getAll() {
        List<Reclamation> reclamations = reclamationRepository.findByDeletedFalse();
        return reclamations.stream()
                .map(reclamation -> modelMapper.map(reclamation, ReclamationDto.class))
                .collect(Collectors.toList());
    }




    public List<ReclamationDto> getAllByBranchId(Long branchId) {
        List<Reclamation> reclamations = reclamationRepository.findByDeletedFalseAndBranchIdOrderByPriorityDescCreateAtDesc(branchId);
        return reclamations.stream()
                .map(reclamation -> modelMapper.map(reclamation, ReclamationDto.class))
                .collect(Collectors.toList());
    }




    public List<ClientInfoDto> getAllClientInfo() {
        List<ClientInfo> clientInfos = contratsRepository.findAll();
        return clientInfos.stream()
                .map(contrat -> modelMapper.map(contrat, ClientInfoDto.class))
                .collect(Collectors.toList());
    }

    public ClientInfoDto getClientInfoByNumPolice(String numPolice) {
        ClientInfo clientInfo = contratsRepository.getClientInfoByNumPolice(numPolice);
        ClientInfoDto clientInfoDto = modelMapper.map(clientInfo, ClientInfoDto.class);
        return clientInfoDto;
    }


    @Override
    public void softDelete(Long id) {
        Optional<Reclamation> reclamation = reclamationRepository.findById(id);
        if (reclamation.isPresent()) {
            Reclamation existingReclamation = reclamation.get();
         //   if (existingReclamation.getStatus() != ReclamationStatus.EN_COURS) {
                //existingReclamation.setDeleteAt(new Date());
                existingReclamation.setActive(false);
                existingReclamation.setDeleted(true);
                reclamationRepository.save(existingReclamation);

           // } else {
             //   throw new IllegalArgumentException("Reclamation must not be in EN_COURS status to be deleted.");

           // }
        } else {
            throw new EntityNotFoundException("Reclamation with id " + id + " not found.");
        }
    }


    public List<ReclamationDto> getReclamationsByTeamId(Long id) {
        List<Reclamation> reclamations = reclamationRepository.findReclamationsByTeamId( id);
        return reclamations.stream()
                .map(reclamation -> modelMapper.map(reclamation, ReclamationDto.class))
                .collect(Collectors.toList());
    }
}