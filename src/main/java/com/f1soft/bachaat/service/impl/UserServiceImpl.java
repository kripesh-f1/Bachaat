package com.f1soft.bachaat.service.impl;

import com.f1soft.bachaat.dto.request.UserRequestDTO;
import com.f1soft.bachaat.dto.response.UserResponseDTO;
import com.f1soft.bachaat.entity.User;
import com.f1soft.bachaat.exception.DataNotFoundException;
import com.f1soft.bachaat.exception.UserAlreadyExistsException;
import com.f1soft.bachaat.repository.RoleRepository;
import com.f1soft.bachaat.repository.UserRepository;
import com.f1soft.bachaat.service.UserService;
import com.f1soft.bachaat.utils.ActivationCodeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ActivationCodeUtil activationCodeUtil;

    private static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public UserResponseDTO addUser(UserRequestDTO userRequestDTO) {
        logger.info("Inside Add User Service");
        User user = userRepository.findByMobileNumber(userRequestDTO.getMobileNumber());
        if (user != null) {
            throw new UserAlreadyExistsException
                    (String.format("User with %s mobile number already exist.",user.getMobileNumber()));
        }
        User u=modelMapper.map(userRequestDTO,User.class);
        u.setActivationCode(activationCodeUtil.getActivationCode());
        u.setRoles(Arrays.asList(roleRepository.findByName("USER")));
        User returnedUser=userRepository.save(u);
        UserResponseDTO userResponseDTO=modelMapper.map(returnedUser,UserResponseDTO.class);
        return userResponseDTO;
    }


    @Override
    public void deleteUser(long id) {
        logger.info("Inside Delete User Service");
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataNotFoundException("User not found with id: " + id);
        }
    }


    @Override
    public List<UserResponseDTO> getUsers() {

        logger.info("Inside Get Users Service");
        List<User> userList= userRepository.findAll();
        if (userList.size()==0 || userList == null) {
            throw new DataNotFoundException("Cannot find users.");
        }
        List<UserResponseDTO> userResponseDTOS=userList.stream()
                    .map(UserResponseDTO :: new).collect(Collectors.toList());
        return userResponseDTOS;
    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) {
        logger.info("Inside Update User Service");
        if (userRequestDTO.getId() == null) {
            throw new DataNotFoundException("User id can not be null");
        }
        if (!userRepository.findById(userRequestDTO.getId()).isPresent()) {
            throw new DataNotFoundException("User with id " + userRequestDTO.getId() + " cannot be found");
        }
        User user=modelMapper.map(userRequestDTO,User.class);
        User u=userRepository.save(user);
        UserResponseDTO userResponseDTO=modelMapper.map(u,UserResponseDTO.class);
        return userResponseDTO;
    }
}
