package com.rasello.auth.user;

import com.rasello.auth.role.Role;
import com.rasello.auth.role.RoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getUser(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostConstruct
    public void onInitialized(){
        if (userRepository.count() > 0) return;
        var userRole= new Role("user", "USER");
        var adminRole = new Role("admin", "ADMIN");
        var savedUserRole = roleRepository.save(userRole);
        var savedAdminRole = roleRepository.save(adminRole);
        var user = new User("user@gmail.com", "N3pal@312!", savedUserRole);
        userRepository.save(user);

        var admin = new User("admin@gmail.com", "N3pal@312!", savedAdminRole);
        userRepository.save(admin);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
