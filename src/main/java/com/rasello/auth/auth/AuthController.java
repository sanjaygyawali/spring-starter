package com.rasello.auth.auth;

import com.rasello.auth.mail.EmailService;
import com.rasello.auth.mail.Mail;
import com.rasello.auth.response.ApiResponse;
import com.rasello.auth.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final UserService userService;
    private final EmailService emailService;
    private final ResetTokenService resetTokenService;
    private final PasswordEncoder passwordEncoder;

    @Value("${server.domain}")
    protected String domain;

    @Value("mail.username")
    protected String fromEmail;

    public AuthController(UserService userService, EmailService emailService, ResetTokenService resetTokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.resetTokenService = resetTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("forgot-password")
    public ApiResponse<?> forgotPassword(@RequestParam String email) {
        var user = userService.getByEmail(email).orElse(null);
        if (user == null) {
            return new ApiResponse<>(404, "Email does not exist");
        }
        var token = UUID.randomUUID().toString();
        var expiryDate = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        var resetToken = new ResetToken(user.getId(), token, expiryDate);
        resetTokenService.save(resetToken);
        var mail = new Mail();
        mail.setFrom(fromEmail);
        mail.setTo(user.getEmail());
        mail.setSubject("Password Recovery");
        mail.setTemplate("forgot-password");
        mail.setProps(
                Map.of(
                        "name", user.getFirstName(),
                        "link", String.format("%s/api/auth/reset-password?token=%s", domain, token)
                )
        );
        new Thread(() -> {
            try {
                emailService.sendMail(mail);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();
        return new ApiResponse<>(200, String.format("A new link is sent to %s to reset your password", user.getEmail()));
    }

    @GetMapping("reset-password")
    public ApiResponse<ResetTokenDto> resetPassword(@RequestParam String token) {
        var resetToken = resetTokenService.getByToken(token).orElse(null);
        if (resetToken == null) {
            return new ApiResponse<>(404, "Invalid token");
        }
        if (resetToken.getExpiredAt() == null && resetToken.getExpiredAt().before(new Date())) {
            return new ApiResponse<>(500, "Token already expired");
        }
        var user = userService.getUser(resetToken.getUserId());

        return new ApiResponse<>(200, "Success", new ResetTokenDto(token, user.getEmail(), user.getId().toString()));
    }

    @PutMapping("reset-password/{token}")
    public ApiResponse<?> resetPassword(@PathVariable String token, @Valid @RequestBody ResetPasswordDto passwordDto) {
        var resetToken = resetTokenService.getByUserAndToken(passwordDto.getUserId(), token).orElse(null);
        if (resetToken == null) {
            return new ApiResponse<>(404, "Invalid token");
        }
        if (resetToken.getExpiredAt() == null && resetToken.getExpiredAt().before(new Date())) {
            return new ApiResponse<>(500, "Token already expired");
        }
        var user = userService.getUser(resetToken.getUserId());
        user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        userService.save(user);
        resetToken.setExpiredAt(new Date());
        resetTokenService.save(resetToken);
        return new ApiResponse<>(200, "Password successfully reset");
    }
}
