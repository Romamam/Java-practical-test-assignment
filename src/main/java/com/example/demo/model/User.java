package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
public class User {

    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String address;
    private int phoneNumber;

    public User(String email, String firstName, String lastName, String birthDate){
        this.id = UUID.randomUUID();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
    public User(String email, String firstName, String lastName, String birthDate, String address){
        this.id = UUID.randomUUID();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
    }
    public User(String email, String firstName, String lastName, String birthDate, int phoneNumber){
        this.id = UUID.randomUUID();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

}
