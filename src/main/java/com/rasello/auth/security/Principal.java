package com.rasello.auth.security;

import com.rasello.auth.role.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Principal {
    private UUID id;
    private String name;
    private String email;
    private Role role;
    public List<String> permissions;

}
