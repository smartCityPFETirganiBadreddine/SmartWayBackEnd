package com.example.backendsmartcities.repository;

import com.example.backendsmartcities.entity.Materiel;
import com.example.backendsmartcities.entity.Role;
import com.example.backendsmartcities.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
    Optional<Role> findById(Integer id);
    @Modifying
    @Transactional
    @Query(value = "UPDATE role u " +
            "SET u.deleted = true, u.deleted_at = CURRENT_TIMESTAMP, u.is_active = false, u.updated_at = CURRENT_TIMESTAMP, " +
            "WHERE u.id = :id", nativeQuery = true)
    void delete(Long id);
}
