package com.employee.ride.service;

import com.employee.ride.entity.User;

import java.util.List;

public interface UserService {

    /**
     * Method that return the list of user
     * @return a list of user
     */
    List<User> getListOfUser();

    /**
     * Method creating a user
     * @param user
     * @return the user created
     */
    User createUser(User user);
}
