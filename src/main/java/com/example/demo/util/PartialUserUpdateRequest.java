package com.example.demo.util;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class PartialUserUpdateRequest {

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String address;
    private Integer phoneNumber;

}