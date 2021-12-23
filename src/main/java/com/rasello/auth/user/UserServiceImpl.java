package com.rasello.auth.user;

import com.rasello.auth.exception.ExistingRecordException;
import com.rasello.auth.exception.RecordNotFoundException;
import com.rasello.auth.role.Role;
import com.rasello.auth.role.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        var userOpt = userRepository.findByEmail(username);
        return userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User not found"));
    }


    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("Email does not exist"));
    }


    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> getAll(Pageable pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    @Override
    public User create(User user) throws ExistingRecordException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.findByEmail(user.getEmail()).ifPresent((u) -> {
            throw new ExistingRecordException("Email already registered");
        });
        return userRepository.save(user);
    }

    @PostConstruct
    protected void onInitialized() {
        userRepository.deleteAll();
        var userRole = new Role("user", "USER");
        var adminRole = new Role("admin", "ADMIN");
        var savedUserRole = roleRepository.save(userRole);
        var savedAdminRole = roleRepository.save(adminRole);
        var user = new User("lamsal.av@gmail.com", passwordEncoder.encode("N3pal@312!"), "Abhisek", null, "Lamsal", savedUserRole);
        userRepository.save(user);

        var admin = new User("admin@gmail.com", passwordEncoder.encode("N3pal@312!"), "Abhisek", null, "Lamsal", savedAdminRole);
        userRepository.save(admin);
    }
}
