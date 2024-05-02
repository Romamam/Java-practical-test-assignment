package com.example.demo.util;

import lombok.Data;

@Data
public class PartialUserUpdateRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String address;
    private Integer phoneNumber;

}