package com.example.backendsmartcities.repository;

import com.example.backendsmartcities.entity.PasswordResetToken;
import com.example.backendsmartcities.mail.ResetPasswordRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
}
