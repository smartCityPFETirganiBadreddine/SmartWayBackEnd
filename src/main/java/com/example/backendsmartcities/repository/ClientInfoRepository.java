package com.example.backendsmartcities.repository;

import com.example.backendsmartcities.entity.ClientInfo;
import com.example.backendsmartcities.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
/**
 * Author: Badreddine TIRGANI
 */
@Repository
public interface ClientInfoRepository extends JpaRepository<ClientInfo,Long> {
    ClientInfo findByAddress(String s);
    ClientInfo findByClientName(String s);
    ClientInfo findByPhone(String s);
    ClientInfo findBySector(Sector sector);
    ClientInfo findByTourNumber(String s);
    @Modifying
    @Transactional
    @Query(value = "UPDATE clientInfo u " +
            "SET u.deleted = true, u.deleted_at = CURRENT_TIMESTAMP, u.is_active = false, u.updated_at = CURRENT_TIMESTAMP, " +
            "WHERE u.id = :id", nativeQuery = true)
    void delete(Long id);
}
