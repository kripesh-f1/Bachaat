package com.kat.bachaat.controller;

import com.kat.bachaat.model.User;
import com.kat.bachaat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserController
{
    private  static Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        logger.info("fetch getUsers method");
        List<User> users = userService.getUsers();
        if(users==null||users.size()==0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
