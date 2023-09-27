package com.example.backendsmartcities.service;

import com.example.backendsmartcities.dto.NotificationDto;
import com.example.backendsmartcities.dto.UserDto;

import java.util.List;
import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
public class NotificationService implements BaseService<NotificationDto> {
    @Override
    public NotificationDto save(NotificationDto obj) throws Exception {
        return null;
    }

    @Override
    public NotificationDto update(long id, NotificationDto obj) throws Exception {
        return null;
    }

    @Override
    public void softDelete(Long id) {

    }

    @Override
    public Optional<NotificationDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<NotificationDto> getAll() {
        return null;
    }
}
