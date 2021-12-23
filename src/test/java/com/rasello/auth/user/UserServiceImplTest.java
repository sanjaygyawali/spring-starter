package com.rasello.auth.user;

import com.rasello.auth.exception.ExistingRecordException;
import com.rasello.auth.exception.RecordNotFoundException;
import com.rasello.auth.role.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void createShouldSaveDataToDatabase() throws ExistingRecordException {
        var password = "password";
        User user = User.builder()
                .email("janna@email.com")
                .password(password)
                .firstName("Janna")
                .lastName("Doe")
                .build();

        when(passwordEncoder.encode(user.getPassword())).thenReturn(new BCryptPasswordEncoder(12).encode(user.getPassword()));

        when(userRepository.save(any(User.class))).thenAnswer(answer -> {
            var answerUser = (User) answer.getArgument(0);
            answerUser.setId(UUID.randomUUID());
            return answerUser;
        });


        var savedUser = userService.create(user);

        verify(passwordEncoder, times(1)).encode(password);
        verify(userRepository, times(1)).save(user);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    void createShouldThrowExceptionIfUsernameExists() throws ExistingRecordException {
        var email = "janna@email.com";
        User user = User.builder()
                .email(email)
                .password("password")
                .build();
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(any(String.class))).thenReturn(any(String.class));

        assertThatThrownBy(() -> userService.create(user)).isInstanceOf(ExistingRecordException.class);
        verify(userRepository, times(1)).findByEmail(email);

    }

    @Test
    void getAll() {
        User user1 = User.builder()
                .email("janna@email.com")
                .password("password")
                .firstName("Janna")
                .lastName("Doe")
                .build();
        User user2 = User.builder()
                .email("janna@email.com")
                .password("password")
                .firstName("Janna")
                .lastName("Doe")
                .build();
        var userList = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);
        assertThat(userService.getAll()).isInstanceOf(List.class)
                .containsExactly(user1, user2);
    }

    @Test
    void testGetAllWithPageRequestReturnsUserList() {
        User user1 = User.builder()
                .email("janna@email.com")
                .password("password")
                .firstName("Janna")
                .lastName("Doe")
                .build();
        User user2 = User.builder()
                .email("janna@email.com")
                .password("password")
                .firstName("Janna")
                .lastName("Doe")
                .build();
        var userList = Arrays.asList(user1, user2);
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(userList));

        assertThat(userService.getAll(PageRequest.of(0, 2)))
                .isInstanceOf(Page.class);
    }

    @Test
    void testThatOnInitializedSavesDataToRepository() {
        when(passwordEncoder.encode(any(String.class))).thenReturn("");
        userService.onInitialized();
        verify(userRepository, times(1)).deleteAll();
        verify(userRepository, times(2)).save(any(User.class));
    }
}