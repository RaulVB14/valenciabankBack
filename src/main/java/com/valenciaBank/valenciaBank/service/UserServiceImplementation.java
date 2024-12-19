package com.valenciaBank.valenciaBank.service;

import com.valenciaBank.valenciaBank.model.User;
import com.valenciaBank.valenciaBank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation extends UserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByDniAndPassword(String dni, String password) {
        return userRepository.findByDniAndPassword(dni, password);
    }

    @Override
    public boolean existsByDni(String dni) {
        return userRepository.existsByDni(dni);
    }

    @Override
    public User getUser(String dni){
        return userRepository.findUserByDni(dni);
    }

}
