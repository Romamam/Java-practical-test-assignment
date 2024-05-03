package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testValidEmail() throws Exception {
        String invalidEmail = "invalidemail";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"email\": \"" + invalidEmail + "\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"birthDate\": \"01.02.1991\" }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testValidBirthDate() throws Exception {
        String invalidBirthDate = "1991-02-01"; // Invalid format
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"email\": \"test@example.com\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"birthDate\": \"" + invalidBirthDate + "\" }"))
                .andExpect(status().isBadRequest());
    }
}