package com.example.demo;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserUnderAgeException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.PartialUserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserService.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Value("${user.min.age}")
    private int minAge;

    private User testUser;
    private UUID testUserId;

    @BeforeEach
    void setup() {
        testUser = new User("test@example.com", "John", "Doe", LocalDate.now().minusYears(20).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        testUserId = UUID.randomUUID();
        testUser.setId(testUserId);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        List<User> users = userService.getAllUsers();
        assertFalse(users.isEmpty(), "User list should not be empty");
    }

    @Test
    void createUser_ShouldAddUser_WhenAgeIsAboveMinimum() {
        assertDoesNotThrow(() -> userService.createUser(testUser));
        assertTrue(userService.getAllUsers().contains(testUser));
    }

    @Test
    void createUser_ShouldThrowException_WhenUserIsUnderage() {
        User underageUser = new User("underage@example.com", "Jane", "Doe", LocalDate.now().minusYears(minAge - 1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        Exception exception = assertThrows(UserUnderAgeException.class, () -> userService.createUser(underageUser));
        assertEquals("User must be at least 18 years old", exception.getMessage());
    }

    @Test
    void deleteUser_ShouldRemoveUser() throws UserUnderAgeException {
        userService.createUser(testUser);
        userService.deleteUser(testUserId);
        assertNull(userService.getUserById(testUserId));
    }

    @Test
    void updateOneOrSomeUserFields_ShouldUpdateFields() throws UserNotFoundException, UserUnderAgeException {
        userService.createUser(testUser);
        PartialUserUpdateRequest updateRequest = new PartialUserUpdateRequest();
        updateRequest.setEmail("updated@example.com");
        User updatedUser = userService.updateOneOrSomeUserFields(testUserId, updateRequest);
        assertEquals("updated@example.com", updatedUser.getEmail());
    }

    @Test
    void updateOneOrSomeUserFields_ShouldThrowUserNotFoundException() {
        UUID randomId = UUID.randomUUID();
        PartialUserUpdateRequest updateRequest = new PartialUserUpdateRequest();
        assertThrows(UserNotFoundException.class, () -> userService.updateOneOrSomeUserFields(randomId, updateRequest));
    }
}
