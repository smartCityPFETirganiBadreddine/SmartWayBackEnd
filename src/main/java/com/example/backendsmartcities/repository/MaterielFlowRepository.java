package com.example.backendsmartcities.repository;

import com.example.backendsmartcities.entity.MaterielFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
@Repository
public interface MaterielFlowRepository extends JpaRepository<MaterielFlow,Long> {
    Optional<MaterielFlow> findById(Long aLong);
    MaterielFlow findByActionDate(Date date);
    MaterielFlow findByMotif(String s);
    @Modifying
    @Transactional
    @Query(value = "UPDATE materielFlow u " +
            "SET u.deleted = true, u.deleted_at = CURRENT_TIMESTAMP, u.is_active = false, u.updated_at = CURRENT_TIMESTAMP, " +
            "WHERE u.id = :id", nativeQuery = true)
    void delete(Long id);

}
