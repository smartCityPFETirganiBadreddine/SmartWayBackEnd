package com.example.backendsmartcities.repository;

import com.example.backendsmartcities.entity.Branch;
import com.example.backendsmartcities.entity.FamilyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
/**
 * Author: Badreddine TIRGANI
 */
@Repository
public interface FamilyTypeRepository extends JpaRepository<FamilyType,Long> {
    FamilyType findByCode(int s);
    FamilyType findByName(String s);
    FamilyType findByBranch(Branch branch);
    @Modifying
    @Transactional
    @Query(value = "UPDATE familyType u " +
            "SET u.deleted = true, u.deleted_at = CURRENT_TIMESTAMP, u.is_active = false, u.updated_at = CURRENT_TIMESTAMP, " +
            "WHERE u.id = :id", nativeQuery = true)
    void delete(Long id);

    List<FamilyType> findByDeletedFalse();

    List<FamilyType> findByDeletedFalseAndBranch(Branch branch);
}
