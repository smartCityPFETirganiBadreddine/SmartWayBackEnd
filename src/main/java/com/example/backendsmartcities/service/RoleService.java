package com.example.backendsmartcities.service;

import com.example.backendsmartcities.entity.Role;

import java.util.List;
import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
public class RoleService implements BaseService<Role> {
    @Override
    public Role save(Role obj) throws Exception {
        return null;
    }

    @Override
    public Role update(long id, Role obj) throws Exception {
        return null;
    }

    @Override
    public void softDelete(Long id) {

    }

    @Override
    public Optional<Role> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Role> getAll() {
        return null;
    }

}
