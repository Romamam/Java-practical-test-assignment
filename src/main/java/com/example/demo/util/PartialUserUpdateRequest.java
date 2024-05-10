package com.example.demo.util;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PartialUserUpdateRequest {

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    private String firstName;
    private String lastName;
    @Pattern(regexp = "^\\d{2}\\.\\d{2}\\.\\d{4}$", message = "Birth date must be in the format dd.MM.yyyy")
    private String birthDate;
    private String address;
    private Integer phoneNumber;

}