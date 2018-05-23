package com.kat.bachaat.controller;

import com.kat.bachaat.dao.UserRepository;
import com.kat.bachaat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    UserRepository repo;

    @GetMapping
    public ResponseEntity<List<User>> test(){
        List<User> users = repo.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
