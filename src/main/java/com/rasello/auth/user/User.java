package com.rasello.auth.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rasello.auth.role.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    private String email;

    @JsonIgnore
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(columnDefinition = "boolean default false")
    private Boolean active;

    @Column(columnDefinition = "boolean default false")
    private Boolean expired;

    @Column(columnDefinition = "boolean default false")
    private Boolean locked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role == null)
            return new ArrayList<>();
        return role.getPermissions();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        if (expired == null)
            return true;
        return expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (locked == null)
            return true;
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        if (expired == null)
            return true;
        return expired;
    }

    @Override
    public boolean isEnabled() {
        if (this.active == null)
            return true;
        return this.active;
    }

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = true;
        this.expired = false;
        this.locked = false;
    }
}
