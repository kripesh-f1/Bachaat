package com.kat.bachaat.controller;


import com.kat.bachaat.dao.UserRepository;
import com.kat.bachaat.exception.UserAlreadyExistsException;
import com.kat.bachaat.model.User;
import com.kat.bachaat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import javax.validation.Valid;


@RestController
@Validated
@RequestMapping("/user")
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserRepository repo;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        logger.info("fetch getUsers method");
        List<User> users = userService.getUsers();
        if (users == null || users.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        logger.info("Inside add User method of User Controller.");
        User user1 = userService.addUser(user);
        if (user1 == null) {
            throw new UserAlreadyExistsException("User Already Exist!");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
