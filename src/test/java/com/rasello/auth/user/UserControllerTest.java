package com.rasello.auth.user;

import com.rasello.auth.base.CrudService;
import com.rasello.auth.base.TableService;
import com.rasello.auth.exception.RecordNotFoundException;
import com.rasello.auth.role.Role;
import com.rasello.auth.security.JwtTokenHandler;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@WithMockUser
class UserControllerTest {
    @MockBean
    private UserService userService;

    @MockBean
    JwtTokenHandler jwtTokenHandler;

    @MockBean
    TableService tableService;

    @MockBean
    CrudService crudService;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    UserController controller;

    private static final UUID JOHN_ID = UUID.randomUUID();
    private static final UUID JANNA_ID = UUID.randomUUID();
    private static final UUID NOT_EXISTENT_ID = UUID.randomUUID();

    @BeforeEach
    public void setup() {
        var role = Role.builder().id(UUID.randomUUID()).name("User").slug("user").active(true).build();
        var john = User.builder()
                .id(JOHN_ID)
                .firstName("John")
                .lastName("Doe")
                .email("john@email.com")
                .password("john123")
                .active(true)
                .role(role)
                .expired(false)
                .expired(false)
                .build();
        var janna = User.builder()
                .id(JANNA_ID)
                .firstName("Janna")
                .lastName("Doe")
                .email("janna@email.com")
                .password("janna123")
                .active(true)
                .role(role)
                .expired(false)
                .expired(false)
                .build();
        when(crudService.getAll(User.class)).thenReturn(Arrays.asList(john, janna));
        when(crudService.get(JANNA_ID, User.class)).thenReturn(janna);
        when(crudService.get(NOT_EXISTENT_ID, User.class) ).thenThrow(new RecordNotFoundException("Record not found"));
    }

    @Test
    public void testGetAllUsersReturnsBaseListResponse() throws Exception {
        var actions = mockMvc.perform(MockMvcRequestBuilders.get("/api/users")).andExpect(status().isOk());
        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].email").value("john@email.com"));

    }

    @Test
    public void testGetUserByIdReturnsUserWithThatId() throws Exception {
        var actions = mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", JANNA_ID)).andExpect(status().isOk());
        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(JANNA_ID.toString()));

    }

    @Test
    public void testGetUserByIdReturns404OnNonExistingId() throws Exception {
        var actions = mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", NOT_EXISTENT_ID)).andExpect(status().isOk());
        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.data").value(IsNull.nullValue()));

    }


}