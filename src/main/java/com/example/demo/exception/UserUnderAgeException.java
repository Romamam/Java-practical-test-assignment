package com.example.demo.exception;

public class UserUnderAgeException extends Exception{

    public UserUnderAgeException(String message){
        super(message);
    }
}
