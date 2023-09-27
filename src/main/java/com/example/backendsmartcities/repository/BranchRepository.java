package com.example.backendsmartcities.repository;

import com.example.backendsmartcities.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
/**
 * Author: Badreddine TIRGANI
 */
@Repository
public interface BranchRepository extends JpaRepository<Branch,Long> {
    Branch findByName(String name);

    Branch findByIsActive(boolean isActive);

    @Modifying
    @Transactional
    @Query(value = "UPDATE branch u " +
            "SET u.deleted = true, u.deleted_at = CURRENT_TIMESTAMP, u.is_active = false, u.updated_at = CURRENT_TIMESTAMP, " +
           "WHERE u.id = :id", nativeQuery = true)
    void delete(Long id);
    Branch getBranchById(Long id);
}
