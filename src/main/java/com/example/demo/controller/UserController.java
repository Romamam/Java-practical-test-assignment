package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.PartialUserUpdateRequest;
import com.example.demo.util.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) throws UserNotFoundException {
        User user = userService.getUserById(id);
        if(user != null){
            return ResponseEntity.ok(user);
        } else {
            throw new UserNotFoundException("Uesr with ID " + id + " not found");
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Object> updateOneOrSomeUserFields(@PathVariable UUID id,
                                                            @RequestBody PartialUserUpdateRequest request) {
        try {
            User updatedUser = userService.updateOneOrSomeUserFields(id, request);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException ex) {
            String errorMessage = "User with ID " + id + " not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateAllUserFields(@PathVariable UUID id, @RequestBody User updatedUserFields){
        try{
            User updatedUser = userService.updateAllUserFields(id, updatedUserFields);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException ex){
            String errorMessage = "User with ID " + id + " not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        String errorMessage = "User not found: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}
