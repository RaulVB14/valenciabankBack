package com.valenciaBank.valenciaBank.controller;

import com.valenciaBank.valenciaBank.model.Account;
import com.valenciaBank.valenciaBank.model.User;
import com.valenciaBank.valenciaBank.service.AccountService;
import com.valenciaBank.valenciaBank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.valenciaBank.valenciaBank.utils.Methods.generateAccountNumber;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/add")
    public ResponseEntity<User> add(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        Account account = new Account();
        account.setNumber(generateAccountNumber());
        account.setBalance(0.0);
        account.setUser(savedUser);
        Account savedAccount = accountService.saveAccount(account);
        savedUser.setAccount(savedAccount);
        userService.saveUser(savedUser);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.getUserByDniAndPassword(user.getDni(), user.getPassword());
    }

    @GetMapping("/get/{dni}")
    public User findUser(@PathVariable String dni) {
        return userService.getUser(dni);
    }

    @GetMapping("/exists/{dni}")
    public boolean existsByDni(@PathVariable String dni) {
        return userService.existsByDni(dni);
    }
}

