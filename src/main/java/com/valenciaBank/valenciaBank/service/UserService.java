package com.valenciaBank.valenciaBank.service;

import com.valenciaBank.valenciaBank.model.User;
import com.valenciaBank.valenciaBank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByDniAndPassword(String dni, String password) {
        return userRepository.findByDniAndPassword(dni, password);
    }

    public boolean existsByDni(String dni) {
        return userRepository.existsByDni(dni);
    }

    public User getUser(String dni){
        return userRepository.findUserByDni(dni);
    }
}
