package com.rasello.auth.auth;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reset_tokens")
public class ResetToken {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false, unique = true)
    private String token;

    private Date createdAt;

    private Date expiredAt;

    @PrePersist
    public void onCreate(){
        if (this.createdAt == null){
            this.createdAt = new Date();
        }
    }

    public ResetToken(UUID userId, String token, Date expiredAt) {
        this.userId = userId;
        this.token = token;
        this.expiredAt = expiredAt;
    }
}
