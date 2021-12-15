package com.rasello.auth.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResetTokenRepository extends JpaRepository<ResetToken, UUID> {
    Optional<ResetToken> findByToken(String token);
    Optional<ResetToken> findByUserIdAndToken(UUID userId, String token);
}
