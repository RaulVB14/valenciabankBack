package com.valenciaBank.valenciaBank.repository;

import com.valenciaBank.valenciaBank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByDniAndPassword(String dni, String password);
    boolean existsByDni(String dni);
    User findUserByDni(String dni);
}
