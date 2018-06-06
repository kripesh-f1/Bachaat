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
import static com.f1soft.bachaat.utils.MessageConstant.*;

@RestController
@RequestMapping(API_VER + USERS_PATH)
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @PostMapping(DELETE_PATH)
    public ResponseEntity<ApiMessageResponse> deleteUser(@RequestParam long id) {
        logger.info(String.format("User Controller: deleteUser(): with id: %d", id));
        userService.deleteUser(id);
        ApiMessageResponse apiMessageResponse = new ApiMessageResponse();
        apiMessageResponse.setMessage(String.format(USER_DELETE_MESSAGE, id));
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
        logger.info("User Controller: updateUser(): START");
        ApiMessageResponse apiMessageResponse = new ApiMessageResponse();
        userService.updateUser(userRequestDTO);
        apiMessageResponse.setMessage(String.format(USER_UPDATE_MESSAGE, userRequestDTO.getId()));
        return new ResponseEntity<>(apiMessageResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiMessageResponse> addUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        logger.info("User Controller: addUser(): START");
        ApiMessageResponse apiMessageResponse = new ApiMessageResponse();
        userService.addUser(userRequestDTO);
        apiMessageResponse.setMessage(USER_ADD_MESSAGE);
        return new ResponseEntity<>(apiMessageResponse, HttpStatus.OK);
    }
}
