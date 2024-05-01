package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    {
        users.add(new User("user1@exasdample.com", "Joasdhn", "Dasdoe","30.04.2002"));
        users.add(new User("user1@exasdasdample.com", "Joasdhasdn", "Dasasddoe","30.04.2003"));
        users.add(new User("user1@exasasddample.com", "Joasdhn", "Dasasasdddoe","30.04.2004"));
        users.add(new User("user1@exasasddample.com", "Joasdhn", "Daasdsdoe","30.04.2005"));
    }
}