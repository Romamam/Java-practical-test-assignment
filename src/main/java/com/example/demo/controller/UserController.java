package com.example.demo.controller;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.PartialUserUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) throws UserNotFoundException {
        User user = userService.getUserById(id);
        if(user != null){
            return ResponseEntity.ok(user);
        } else {
            throw new UserNotFoundException("Uesr with ID " + id + " not found");
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> updateOneOrSomeUserFields(@PathVariable UUID id,
                                                            @Valid @RequestBody PartialUserUpdateRequest request) {
        try {
            User updatedUser = userService.updateOneOrSomeUserFields(id, request);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException ex) {
            String errorMessage = "User with ID " + id + " not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateAllUserFields(@PathVariable UUID id, @Valid @RequestBody User updatedUserFields){
        try{
            User updatedUser = userService.updateAllUserFields(id, updatedUserFields);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException ex){
            String errorMessage = "User with ID " + id + " not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with ID " + id + " has been deleted successfully");
    }

    @GetMapping("range-list-from-to/{fromDate}-{toDate}")
    public ResponseEntity<List<User>> getUsersByBirthDateRange(
            @PathVariable String fromDate, @PathVariable String toDate) {
        try {
            List<User> users = userService.getUsersByBirthDateRange(fromDate, toDate);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }
}
