package com.example.backendsmartcities.service;

import com.example.backendsmartcities.entity.Sector;

import java.util.List;
import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
public class SectorService implements BaseService<Sector> {
    @Override
    public Sector save(Sector obj) throws Exception {
        return null;
    }

    @Override
    public Sector update(long id, Sector obj) throws Exception {
        return null;
    }

    @Override
    public void softDelete(Long id) {

    }

    @Override
    public Optional<Sector> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Sector> getAll() {
        return null;
    }
}
