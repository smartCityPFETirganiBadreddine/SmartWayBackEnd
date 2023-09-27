package com.example.backendsmartcities.repository;

import com.example.backendsmartcities.entity.Role;
import com.example.backendsmartcities.entity.Team;
import com.example.backendsmartcities.entity.User;
import com.sun.xml.bind.v2.model.core.ID;
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
public interface UserRepository extends JpaRepository<User, ID> {
    User findByUserName(String userName);
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String Email);

    boolean existsByUserName(String userName);


    @Query(value = "select * from users u where u.id = :id and u.deleted = false;", nativeQuery = true)
    Optional<User> findById(@Param("id") Long id);

    @Modifying
    @Transactional
    /*@Query(value = "UPDATE users u " +
            "LEFT JOIN team pu ON u.team_id = pu.id " +
            "LEFT JOIN branch t ON t.id = u.branch_id " +
            "SET u.deleted = true, u.deleted_at = CURRENT_TIMESTAMP, u.is_active = false, u.updated_at = CURRENT_TIMESTAMP, " +
            "pu.deleted = true, pu.deleted_at = CURRENT_TIMESTAMP, pu.updated_at = CURRENT_TIMESTAMP, " +
            "t.user_id = null WHERE u.id = :id", nativeQuery = true)*/
    @Query(value = "UPDATE users u " +
            "SET u.deleted = true, u.deleted_at = CURRENT_TIMESTAMP, u.is_active = false, u.updated_at = CURRENT_TIMESTAMP, " +
            "WHERE u.id = :id", nativeQuery = true)
    void delete(@Param("id") Long id);

    List<User> findAll();

    boolean existsById(Long id);

    List<User> findByTeam(Team team);

    List<User> findByRoles(Role role);
}
