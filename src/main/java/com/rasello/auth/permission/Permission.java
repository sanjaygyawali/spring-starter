package com.rasello.auth.permission;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "permissions")
public class Permission implements GrantedAuthority {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String slug;

    private String name;

    private String description;

    @Override
    public String getAuthority() {
        return slug;
    }
}
