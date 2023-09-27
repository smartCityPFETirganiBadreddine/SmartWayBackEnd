package com.example.backendsmartcities.repository;

import com.example.backendsmartcities.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
/**
 * Author: Badreddine TIRGANI
 */
@Repository
public interface StatusRepository extends JpaRepository<Status,Long> {
    //Status findByReclamation(String name);

    //Branch findByIsActive(boolean isActive);

    @Modifying
    @Transactional
    @Query(value = "UPDATE status u " +
            "SET u.deleted = true, u.deleted_at = CURRENT_TIMESTAMP, u.is_active = false, u.updated_at = CURRENT_TIMESTAMP, " +
           "WHERE u.id = :id", nativeQuery = true)
    void delete(Long id);
}
