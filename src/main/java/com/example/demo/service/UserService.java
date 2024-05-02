package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.util.PartialUserUpdateRequest;
import com.example.demo.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Value("${user.min.age}")
    private int minAge;

    private List<User> users = new ArrayList<>();

    {
        users.add(new User(UUID.randomUUID(),"user1@exasdample.com", "Joasdhn", "Dasdoe","30.04.2002"));
        users.add(new User(UUID.randomUUID(),"user1@exasdasdample.com", "Joasdhasdn", "Dasasddoe","30.04.2003"));
        users.add(new User(UUID.randomUUID(),"user1@exasasddample.com", "Joasdhn", "Dasasasdddoe","30.04.2004"));
        users.add(new User(UUID.randomUUID(),"user1@exasasddample.com", "Joasdhn", "Daasdsdoe","30.04.2005"));
    }

    public List<User> getAllUsers(){
        return users;
    }

    public User createUser(User user){
        if(calculateAge(user) >= minAge){
            users.add(user);
            return user;
        }
        return null;
    }

    public User updateOneOrSomeUserFields(UUID id, PartialUserUpdateRequest request) throws UserNotFoundException {
        User userToUpdate = getUserById(id);

        if (userToUpdate != null) {
            if (request.getEmail() != null) {
                userToUpdate.setEmail(request.getEmail());
            }
            if (request.getFirstName() != null) {
                userToUpdate.setFirstName(request.getFirstName());
            }
            if (request.getLastName() != null) {
                userToUpdate.setLastName(request.getLastName());
            }
            if (request.getBirthDate() != null) {
                if (calculateAge(userToUpdate) < minAge) {
                    throw new IllegalArgumentException("User must be at least 18 years old.");
                }
                userToUpdate.setBirthDate(request.getBirthDate());
            }
            if (request.getAddress() != null) {
                userToUpdate.setAddress(request.getAddress());
            }
            if (request.getPhoneNumber() != null) {
                userToUpdate.setPhoneNumber(request.getPhoneNumber());
            }

            return userToUpdate;
        } else {
            throw new UserNotFoundException("User with ID " + " not found");
        }
    }

    public User updateAllUserFields(UUID id, User updatedUserFields) throws UserNotFoundException {
        User userToUpdate = getUserById(id);

        if (userToUpdate != null) {
            userToUpdate.setEmail(updatedUserFields.getEmail());
            userToUpdate.setFirstName(updatedUserFields.getFirstName());
            userToUpdate.setLastName(updatedUserFields.getLastName());
            userToUpdate.setBirthDate(updatedUserFields.getBirthDate());
            userToUpdate.setAddress(updatedUserFields.getAddress());
            userToUpdate.setPhoneNumber(updatedUserFields.getPhoneNumber());

            return userToUpdate;
        } else {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
    }

    public void deleteUser(UUID id){
        User user = getUserById(id);
        users.remove(user);
    }

    public List<User> getUsersByBirthDateRange(String fromDateStr, String toDateStr) {
        LocalDate fromDate = parseToDate(fromDateStr);
        LocalDate toDate = parseToDate(toDateStr);

        if (fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException("'From' date should be before 'To' date");
        }

        return users.stream()
                .filter(user -> {
                    LocalDate userBirthDate = parseToDate(user.getBirthDate());
                    return userBirthDate.isAfter(fromDate) && userBirthDate.isBefore(toDate);
                })
                .collect(Collectors.toList());
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