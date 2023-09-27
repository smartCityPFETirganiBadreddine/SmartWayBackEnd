package com.example.backendsmartcities.repository;

import com.example.backendsmartcities.entity.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
@Repository
public interface MaterielRepository extends JpaRepository<Materiel,Long> {
    Materiel getMaterielById(Long id);
    @Modifying
    @Transactional
    @Query("UPDATE Materiel m SET m.deleted = true, m.deleteAt = CURRENT_TIMESTAMP, m.isActive = false, m.updatedAt = CURRENT_TIMESTAMP WHERE m.id = :id")
    void delete(@Param("id") Long id);
}
