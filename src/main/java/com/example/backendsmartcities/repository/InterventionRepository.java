package com.example.backendsmartcities.repository;

import com.example.backendsmartcities.entity.Intervention;
import com.example.backendsmartcities.entity.Reclamation;
import com.example.backendsmartcities.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Author: Badreddine TIRGANI
 */
@Repository
public interface InterventionRepository extends JpaRepository<Intervention,Long> {
    Intervention findByComment(String s);
    Intervention findByInterventionDateTime(Date date);

    Intervention findByLatitude(String s);
    Intervention findByLongitude(String s);

    Intervention findByNumberMaterial(int i);
    Intervention findByPickupDateTime(Date date);
    Intervention findByPlanningDateTime(Date date);
   // Intervention findByStatus(int i);
    List<Intervention> findByReclamation(Reclamation reclamation);

    Intervention findByTeam(Team team);
    @Modifying
    @Transactional
    @Query(value = "UPDATE intervention u " +
            "SET u.deleted = true, u.deleted_at = CURRENT_TIMESTAMP, u.is_active = false, u.updated_at = CURRENT_TIMESTAMP, " +
            "WHERE u.id = :id", nativeQuery = true)
    void delete(Long id);

    List<Intervention> findByDeletedFalse();

    List<Intervention> findByDeletedFalseAndBranchId(Long branchId);

    List<Intervention> findByReclamationId(Long id);
}
