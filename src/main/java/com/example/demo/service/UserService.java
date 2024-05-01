package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    @Value("${user.min.age}")
    private int minAge;

    private List<User> users = new ArrayList<>();

    {
        users.add(new User("user1@exasdample.com", "Joasdhn", "Dasdoe","30.04.2002"));
        users.add(new User("user1@exasdasdample.com", "Joasdhasdn", "Dasasddoe","30.04.2003"));
        users.add(new User("user1@exasasddample.com", "Joasdhn", "Dasasasdddoe","30.04.2004"));
        users.add(new User("user1@exasasddample.com", "Joasdhn", "Daasdsdoe","30.04.2005"));
    }

    public List<User> getAllUsers(){
        return users;
    }

    public User createUser(User user){
        if(calculateAge(user) >= minAge){
            users.add(user);
        }
        return null;
    }

    public User updateOneOrSomeUserFields(UUID id, String email, String firstName, String lastName, String birthDate,
                                 String address, Integer phoneNumber) throws UserNotFoundException {
        User userToUpdate = getUserById(id);

        if (userToUpdate != null) {
            if (email != null) {
                userToUpdate.setEmail(email);
            }
            if (firstName != null) {
                userToUpdate.setFirstName(firstName);
            }
            if (lastName != null) {
                userToUpdate.setLastName(lastName);
            }
            if (birthDate != null) {
                if (calculateAge(userToUpdate) < 18) {
                    throw new IllegalArgumentException("User must be at least 18 years old.");
                }
                userToUpdate.setBirthDate(birthDate);
            }
            if (address != null) {
                userToUpdate.setAddress(address);
            }
            if (phoneNumber != null) {
                userToUpdate.setPhoneNumber(phoneNumber);
            }

            return userToUpdate;
        } else {
            throw new UserNotFoundException("User with ID " + " not found");
        }
    }

    public User updateAllUserFields(UUID id, String email, String firstName, String lastName, String birthDate,
                                    String address, int phoneNumber) throws UserNotFoundException {
        User userToUpdate = getUserById(id);

        if (userToUpdate != null) {
            userToUpdate.setEmail(email);
            userToUpdate.setFirstName(firstName);
            userToUpdate.setLastName(lastName);
            userToUpdate.setBirthDate(birthDate);
            userToUpdate.setAddress(address);
            userToUpdate.setPhoneNumber(phoneNumber);

            return userToUpdate;
        } else {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
    }

    public void deleteUser(UUID id){
        User user = getUserById(id);
        users.remove(user);
    }

    public User getUserById(UUID id) {
        return users.stream()
                .filter(user -> id.equals(user.getId()))
                .findFirst()
                .orElse(null);
    }

    public int calculateAge(User user) {
        LocalDate birthDate = parseToDate(user.getBirthDate());
        LocalDate currentDate = LocalDate.now();

        int age = currentDate.getYear() - birthDate.getYear();

        if (birthDate.getMonthValue() > currentDate.getMonthValue() ||
                (birthDate.getMonthValue() == currentDate.getMonthValue() && birthDate.getDayOfMonth() > currentDate.getDayOfMonth())) {
            age--;
        }

        return age;
    }

    public LocalDate parseToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(date, formatter);
    }
}