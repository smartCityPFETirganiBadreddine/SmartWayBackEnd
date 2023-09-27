package com.example.backendsmartcities.service;

import com.example.backendsmartcities.dto.MaterielDto;
import com.example.backendsmartcities.entity.Branch;
import com.example.backendsmartcities.entity.Materiel;
import com.example.backendsmartcities.repository.BranchRepository;
import com.example.backendsmartcities.repository.MaterielRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
/**
 * Author: Badreddine TIRGANI
 */
@Service
@Slf4j
public class MaterielService implements BaseService<MaterielDto> {
    @Autowired
    private ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private MaterielRepository materielRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Override
    public MaterielDto save(MaterielDto obj) throws Exception {
        if (obj.getId() != null && materielRepository.existsById(obj.getId())) {
            throw new Exception("This Materiel is already in use.");
        }
        Materiel materiel = materielRepository.save(modelMapper.map(obj, Materiel.class));
        return modelMapper.map(materiel, MaterielDto.class);
    }

    @Override
    public MaterielDto update(long id, MaterielDto obj) throws Exception {
        Materiel materiel = materielRepository.getMaterielById(id);
        Branch branch = branchRepository.getBranchById(obj.getId());
        if (materiel != null) {
            if(branch !=null){
                materiel.setBranch(branch);
            }
            materiel.setName(obj.getName());
            materiel.setUnit(obj.getUnit());
            materiel.setActive(obj.isActive());

            Materiel updatedMateriel = materielRepository.save(materiel);
            return modelMapper.map(updatedMateriel, MaterielDto.class);
        }
        throw new Exception("Failed to update Materiel with id " + id);
    }

    @Override
    public void softDelete(Long id) {
        materielRepository.delete(id);
    }

    @Override
    public Optional<MaterielDto> findById(Long id) {
        Optional<Materiel> sc = materielRepository.findById(id);
        return sc.map(user -> modelMapper.map(user, MaterielDto.class));
    }

    @Override
    public List<MaterielDto> getAll() {
        List<Materiel> all = entityManager.createNamedQuery("findAllMateriel", Materiel.class).getResultList();
        List<MaterielDto> materielDtos = new ArrayList<>();
        for(Materiel materiel:all){
            materielDtos.add(modelMapper.map(materiel,MaterielDto.class));
        }
        return materielDtos;
    }
}
