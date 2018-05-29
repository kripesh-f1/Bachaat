package com.f1soft.bachaat.controller;

import com.f1soft.bachaat.entity.User;
import com.f1soft.bachaat.exception.MobileNumberInvalidException;
import com.f1soft.bachaat.exception.UserAlreadyExistsException;
import com.f1soft.bachaat.responseMessage.ApiMessageResponse;
import com.f1soft.bachaat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

import static com.f1soft.bachaat.utils.ApiConstant.*;

@RestController
@RequestMapping(API_VER + USER_PATH)
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @PostMapping(DELETE_PATH)
    public ResponseEntity<ApiMessageResponse> deleteUser(@RequestParam long id) {

        logger.info("Deleting user with id: " + id);
        boolean user = userService.deleteUser(id);
        if (user) {
            ApiMessageResponse apiMessageResponse = new ApiMessageResponse();
            apiMessageResponse.setMessage("User of id " + id + " has been deleted");
            return new ResponseEntity<>(apiMessageResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        logger.info("Fetch getUsers method");
        List<User> users = userService.getUsers();
        if (users == null || users.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(UPDATE_PATH)
    public ResponseEntity<ApiMessageResponse> updateUser(@RequestBody @Valid User user) {
        logger.info("Inside Update User Controller");
        ApiMessageResponse apiMessageResponse = new ApiMessageResponse();
        if (user.getId() == null) {
            logger.info("User id is null");
            apiMessageResponse.setMessage("User id cannot be null");
            return new ResponseEntity<>(apiMessageResponse, HttpStatus.NOT_FOUND);
        }
        User currentUser = userService.updateUser(user);
        if (currentUser != null) {
            apiMessageResponse.setMessage("User has been updated successfully!");
            return new ResponseEntity<>(apiMessageResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping
    public ResponseEntity<ApiMessageResponse> addUser(@RequestBody @Valid User user) {
        ApiMessageResponse apiMessageResponse = new ApiMessageResponse();
        apiMessageResponse.setMessage("User has been added successfully!");
        logger.info("Inside add User method of User Controller.");
        if (user.getMobileNumber().length() < 10) {
            throw new MobileNumberInvalidException("Mobile number is less than 10");
        }
        User u = userService.addUser(user);
        if (u == null) {
            throw new UserAlreadyExistsException("User Already Exist!");
        } else
            return new ResponseEntity<>(apiMessageResponse, HttpStatus.OK);
    }
}
