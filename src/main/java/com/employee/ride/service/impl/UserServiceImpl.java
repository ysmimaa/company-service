package com.employee.ride.service.impl;

import com.employee.ride.entity.User;
import com.employee.ride.repository.UserRepository;
import com.employee.ride.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getListOfUser() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        if (user != null) {
            return userRepository.save(user);
        }
        return null;
    }
}
