package com.rasello.auth.auth;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ResetTokenService {
    private final ResetTokenRepository resetTokenRepository;

    public ResetTokenService(ResetTokenRepository resetTokenRepository) {
        this.resetTokenRepository = resetTokenRepository;
    }

    public ResetToken save(ResetToken resetToken) {
        return resetTokenRepository.save(resetToken);
    }

    public Optional<ResetToken> getByToken(String token) {
        return resetTokenRepository.findByToken(token);
    }

    public Optional<ResetToken> getByUserAndToken(UUID user, String token) {
        return resetTokenRepository.findByUserIdAndToken(user, token);
    }
}
