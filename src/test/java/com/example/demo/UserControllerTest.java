package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(UUID.randomUUID(), "test@example.com", "John", "Doe", "01.02.1991");
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<User> users = Collections.singletonList(testUser);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(testUser)).thenReturn(testUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.getUserById(testUser.getId())).thenReturn(testUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", testUser.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateOneOrSomeUserFields() throws Exception {
        when(userService.updateOneOrSomeUserFields(testUser.getId(), null)).thenReturn(testUser);

        mockMvc.perform(MockMvcRequestBuilders.patch("/users/{id}", testUser.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateAllUserFields() throws Exception {
        when(userService.updateAllUserFields(testUser.getId(), testUser)).thenReturn(testUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", testUser.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", testUser.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUsersByBirthDateRange() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/range-list-from-to/01.01.1990-01.01.1991")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}