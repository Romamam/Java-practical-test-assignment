package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = "Validation failed: " + Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        String errorMessage = "User not found: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}