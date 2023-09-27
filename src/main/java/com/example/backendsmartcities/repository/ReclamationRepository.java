package com.example.backendsmartcities.repository;

import com.example.backendsmartcities.entity.Materiel;
import com.example.backendsmartcities.entity.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation,Long> {
    Optional<Reclamation> findById(Long aLong);
    @Modifying
    @Transactional
    @Query(value = "UPDATE reclamation u " +
            "SET u.deleted = true, u.deleted_at = CURRENT_TIMESTAMP, u.is_active = false, u.updated_at = CURRENT_TIMESTAMP " +
            "WHERE u.id = :id", nativeQuery = true)
    void delete(Long id);

    List<Reclamation> findByDeletedFalse();

    @Query(value = "select r.* " +
            "from team t inner join intervention i on t.id = i.team_id " +
            "inner join reclamation r on i.reclamation_id = r.id " +
            "where t.id = :id and r.deleted =false ", nativeQuery = true)
    List<Reclamation> findReclamationsByTeamId(Long id);

    List<Reclamation> findByDeletedFalseAndBranchId(Long branchId);


    List<Reclamation> findByDeletedFalseAndBranchIdOrderByPriorityDescCreateAtDesc(Long branchId);
}
