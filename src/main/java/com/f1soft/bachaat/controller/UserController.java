package com.f1soft.bachaat.controller;

import com.f1soft.bachaat.dto.request.UserRequestDTO;
import com.f1soft.bachaat.dto.response.UserResponseDTO;
import com.f1soft.bachaat.dto.response.UserResponseDTOList;
import com.f1soft.bachaat.responseMessage.ApiMessageResponse;
import com.f1soft.bachaat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

import static com.f1soft.bachaat.utils.ApiConstant.*;

@RestController
@RequestMapping(API_VER + USERS_PATH)
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @PostMapping(DELETE_PATH)
    public ResponseEntity<ApiMessageResponse> deleteUser(@RequestParam long id) {
        logger.info("Deleting user with id: " + id);
        userService.deleteUser(id);
        ApiMessageResponse apiMessageResponse = new ApiMessageResponse();
        apiMessageResponse.setMessage("User with id " + id + " has been deleted.");
        return new ResponseEntity<>(apiMessageResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserResponseDTOList> getUsers(Pageable pageable, @RequestParam String sort, @RequestParam String order) {
        logger.info("Fetch getUsers method");
        UserResponseDTOList users = userService.getUsers(pageable, sort, order);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(UPDATE_PATH)
    public ResponseEntity<ApiMessageResponse> updateUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        logger.info("Inside Update User Controller");
        ApiMessageResponse apiMessageResponse = new ApiMessageResponse();
        userService.updateUser(userRequestDTO);
        apiMessageResponse.setMessage("User has been updated successfully.");
        return new ResponseEntity<>(apiMessageResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiMessageResponse> addUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        logger.info("Inside add User method of User Controller.");
        ApiMessageResponse apiMessageResponse = new ApiMessageResponse();
        userService.addUser(userRequestDTO);
        apiMessageResponse.setMessage("User has been added successfully.");
        return new ResponseEntity<>(apiMessageResponse, HttpStatus.OK);
    }

    @GetMapping(GET_USER_PATH)
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable long id) {
        logger.info("Inside get User method of User Controller.");
        UserResponseDTO userResponseDTO=userService.getUser(id);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }
}
