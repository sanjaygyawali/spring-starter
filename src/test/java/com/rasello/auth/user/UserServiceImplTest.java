package com.rasello.auth.user;

import com.rasello.auth.exception.RecordNotFoundException;
import com.rasello.auth.role.Role;
import com.rasello.auth.role.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    private static final UUID JANNA_ID = UUID.randomUUID();

    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }


    @Test
    void shouldLoadUserByUsernameOrThrowError() {
        var janna = User.builder()
                .id(JANNA_ID)
                .firstName("Janna")
                .lastName("Doe")
                .email("janna@email.com")
                .password("janna123")
                .active(true)
                .expired(false)
                .expired(false)
                .build();
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(janna.getEmail())).thenReturn(Optional.of(janna));
        var user = userService.loadUserByUsername("janna@email.com");
        assertThat("janna@email.com").isSameAs(user.getUsername());
        assertThatThrownBy(() -> userService.loadUserByUsername("janna1@mail.com")).isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void getUser() {
        var janna = User.builder()
                .id(JANNA_ID)
                .firstName("Janna")
                .lastName("Doe")
                .email("janna@email.com")
                .password("janna123")
                .active(true)
                .expired(false)
                .expired(false)
                .build();
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        when(userRepository.findById(JANNA_ID)).thenReturn(Optional.of(janna));
        var user = userService.getUser(JANNA_ID);
        assertThat(JANNA_ID).isSameAs(user.getId());
        assertThatThrownBy(() -> userService.getUser(UUID.randomUUID())).isInstanceOf(RecordNotFoundException.class);
    }

    @Test
    void onInitialized() {
    }

    @Test
    void getByEmail() {
        var janna = User.builder()
                .id(JANNA_ID)
                .firstName("Janna")
                .lastName("Doe")
                .email("janna@email.com")
                .password("janna123")
                .active(true)
                .expired(false)
                .expired(false)
                .build();
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(userRepository.findByEmail(janna.getEmail())).thenReturn(Optional.of(janna));

        assertThat(userService.getByEmail(janna.getEmail())).isNotNull();
        assertThat(userService.getByEmail(janna.getEmail()).getEmail()).isSameAs(janna.getEmail());
        assertThatThrownBy(() -> userService.getByEmail(any(String.class))).isInstanceOf(RecordNotFoundException.class);
    }

    @Test
    void save() {
    }

    @Test
    void getAll() {
    }

    @Test
    void testGetAll() {
    }
}