package com.example.backendsmartcities.repository;

import com.example.backendsmartcities.entity.Image;
import com.example.backendsmartcities.entity.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
/**
 * Author: Badreddine TIRGANI
 */
@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findByName(String s);
    Image findByPath(String s);
    Image findByIntervention(Intervention intervention);
    @Modifying
    @Transactional
    @Query(value = "UPDATE image u " +
            "SET u.deleted = true, u.deleted_at = CURRENT_TIMESTAMP, u.is_active = false, u.updated_at = CURRENT_TIMESTAMP, " +
            "WHERE u.id = :id", nativeQuery = true)
    void delete(Long id);
}
