package com.f1soft.bachaat.controller;

import com.f1soft.bachaat.entity.User;
import com.f1soft.bachaat.repository.UserRepository;
import com.f1soft.bachaat.reponseMessage.ApiMessageResponse;
import com.f1soft.bachaat.service.UserService;
import com.f1soft.bachaat.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import javax.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserRepository repo;

    @Autowired
    private UserService userService;

    @PostMapping("/delete")
    public ResponseEntity<ApiMessageResponse> deleteUser(@RequestParam long id) {

        logger.info("Deleting user with id: " + id);
        boolean user = userService.deleteUser(id);
        if (user) {
            ApiMessageResponse apiMessageResponse =new ApiMessageResponse();
            apiMessageResponse.setMessage("User of id "+id+" has been deleted");
            return new ResponseEntity<>(apiMessageResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

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
    public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
        logger.info("Inside add User method of User Controller.");
        User user1 = userService.addUser(user);
        if (user1 == null) {
            throw new UserAlreadyExistsException("User Already Exist!");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
