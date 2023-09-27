package com.example.backendsmartcities.service;

import com.example.backendsmartcities.dto.InterventionFlowDto;
import com.example.backendsmartcities.dto.UserDto;

import java.util.List;
import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
public class InterventionFlowService implements BaseService<InterventionFlowDto> {
    //TODO complete the services here
    @Override
    public InterventionFlowDto save(InterventionFlowDto obj) throws Exception {
        return null;
    }

    @Override
    public InterventionFlowDto update(long id, InterventionFlowDto obj) throws Exception {
        return null;
    }

    @Override
    public void softDelete(Long id) {

    }

    @Override
    public Optional<InterventionFlowDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<InterventionFlowDto> getAll() {
        return null;
    }
}
