package com.f1soft.bachaat.service.impl;

import com.f1soft.bachaat.dto.request.UserRequestDTO;
import com.f1soft.bachaat.dto.response.UserResponseDTO;
import com.f1soft.bachaat.dto.response.UserResponseDTOList;
import com.f1soft.bachaat.entity.User;
import com.f1soft.bachaat.exception.DataNotFoundException;
import com.f1soft.bachaat.exception.UserAlreadyExistsException;
import com.f1soft.bachaat.repository.RoleRepository;
import com.f1soft.bachaat.repository.UserRepository;
import com.f1soft.bachaat.service.UserService;
import com.f1soft.bachaat.utils.ActivationCodeUtil;
import com.f1soft.bachaat.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private ValidatorUtil validatorUtil;

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
    public UserResponseDTOList getUsers(Pageable pageable,String sort,String order) {
        UserResponseDTOList userResponseDTOList=new UserResponseDTOList();
        logger.info("User Service: getUsers(): START");
        Pageable newPageable= validatorUtil.getPageable(pageable,sort,order);
        Page<User> userPage = userRepository.findAll(newPageable);
        if (userPage == null) {
            throw new DataNotFoundException("Cannot find users.");
        }
        long count=userRepository.count();
        List<UserResponseDTO> userResponseDTOS=userPage.getContent().stream()
                .map(UserResponseDTO :: new).collect(Collectors.toList());
        userResponseDTOList.setUserResponseDTOList(userResponseDTOS);
        userResponseDTOList.setRecords(count);
        return userResponseDTOList;
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
