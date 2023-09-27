package com.example.backendsmartcities.repository;

import com.example.backendsmartcities.entity.Intervention;
import com.example.backendsmartcities.entity.InterventionFlow;
import com.example.backendsmartcities.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
/**
 * Author: Badreddine TIRGANI
 */
@Repository
public interface InterventionFlowRepository extends JpaRepository<InterventionFlow,Long> {
    InterventionFlow findByComment(String s);
    InterventionFlow findByMobileId(String s);
    InterventionFlow findByQuantity(double s);

    InterventionFlow findByTrashQte(double s);

    InterventionFlow findByIntervention(Intervention intervention);
    InterventionFlow findByType(Type type);
    @Modifying
    @Transactional
    @Query(value = "UPDATE interventionFlow u " +
            "SET u.deleted = true, u.deleted_at = CURRENT_TIMESTAMP, u.is_active = false, u.updated_at = CURRENT_TIMESTAMP, " +
            "WHERE u.id = :id", nativeQuery = true)
    void delete(Long id);

}
