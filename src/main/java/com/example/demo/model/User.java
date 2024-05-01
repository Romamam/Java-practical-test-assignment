package com.example.demo.model;

import lombok.Data;


@Data
public class User {

    private String email;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String address;
    private int phoneNumber;

    public User(String email, String firstName, String lastName, String birthDate){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
    public User(String email, String firstName, String lastName, String birthDate, String address){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
    }
    public User(String email, String firstName, String lastName, String birthDate, int phoneNumber){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

}
