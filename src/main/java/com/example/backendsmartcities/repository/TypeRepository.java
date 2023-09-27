package com.example.backendsmartcities.repository;

import com.example.backendsmartcities.entity.FamilyType;
import com.example.backendsmartcities.entity.Materiel;
import com.example.backendsmartcities.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
@Repository
public interface TypeRepository extends JpaRepository<Type,Long> {
    Optional<Type> findById(Long aLong);
    @Modifying
    @Transactional
    @Query(value = "UPDATE type u " +
            "SET u.deleted = true, u.deleted_at = CURRENT_TIMESTAMP, u.is_active = false, u.updated_at = CURRENT_TIMESTAMP, " +
            "WHERE u.id = :id", nativeQuery = true)
    void delete(Long id);

    List<Type> findAllByDeletedFalseAndFamilyType(FamilyType familyType);
    List<Type> findAllByDeletedFalse();
}
