package com.example.backendsmartcities.service;

import java.util.List;
import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
public interface BaseService <T>{
    public T save(T obj) throws Exception;
    public T update(long id,T obj) throws Exception;
    public void softDelete(Long id);
    public Optional<T> findById(Long id);



    public List<T> getAll();
}
