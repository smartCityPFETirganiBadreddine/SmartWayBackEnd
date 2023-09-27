package com.example.backendsmartcities.service;

import com.example.backendsmartcities.dto.MaterielFlowDto;


import java.util.List;
import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
public class MaterielFlowService implements BaseService<MaterielFlowDto> {
    @Override
    public MaterielFlowDto save(MaterielFlowDto obj) throws Exception {
        return null;
    }

    @Override
    public MaterielFlowDto update(long id, MaterielFlowDto obj) throws Exception {
        return null;
    }

    @Override
    public void softDelete(Long id) {

    }

    @Override
    public Optional<MaterielFlowDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<MaterielFlowDto> getAll() {
        return null;
    }
}
