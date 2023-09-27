package com.example.backendsmartcities.repository;

import com.example.backendsmartcities.entity.ClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
@Repository
public interface ContratsRepository extends JpaRepository<ClientInfo,Long> {
    Optional<ClientInfo> findById(Long aLong);

    ClientInfo getClientInfoById(Long id);

    ClientInfo getClientInfoByNumPolice(String numPolice);


}
