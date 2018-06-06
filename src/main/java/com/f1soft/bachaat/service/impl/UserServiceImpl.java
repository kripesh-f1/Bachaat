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
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.f1soft.bachaat.utils.MessageConstant.*;

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
        logger.info("User Service: addUser(): START");
        User user = userRepository.findByMobileNumber(userRequestDTO.getMobileNumber());
        if (user != null) {
            throw new UserAlreadyExistsException(String.format(USER_ALREADY_EXISTS, user.getMobileNumber()));
        }
        User u = modelMapper.map(userRequestDTO, User.class);
        u.setActivationCode(activationCodeUtil.getActivationCode());
        u.setRoles(Arrays.asList(roleRepository.findByName("USER")));
        User returnedUser = userRepository.save(u);
        UserResponseDTO userResponseDTO = modelMapper.map(returnedUser, UserResponseDTO.class);
        return userResponseDTO;
    }

    @Override
    public void deleteUser(long id) {
        logger.info(String.format("User Service: deleteUser(): with id: %d", id));
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataNotFoundException(String.format(USER_ID_NOT_FOUND, id));
        }
    }

    @Override
    public UserResponseDTOList getUsers(Pageable pageable,String sort,String order) {
        UserResponseDTOList userResponseDTOList=new UserResponseDTOList();
        logger.info("User Service: getUsers(): START");
        Pageable newPageable= validatorUtil.getPageable(pageable,sort,order);
        Page<User> userPage = userRepository.findAll(newPageable);
        if (userPage == null) {
            throw new DataNotFoundException(CANNOT_FIND_USERS);
        }
        long count=userRepository.count();
        List<UserResponseDTO> userResponseDTOS=userPage.getContent().stream()
                .map(UserResponseDTO :: new).collect(Collectors.toList());
        userResponseDTOList.setUserResponseDTOList(userResponseDTOS);
        userResponseDTOList.setCount(count);
        return userResponseDTOList;

    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) {
        logger.info("User Service: updateUser(): START");
        if (userRequestDTO.getId() == null) {
            throw new DataNotFoundException(USER_ID_CANNOT_BE_NULL);
        }
        if (!userRepository.findById(userRequestDTO.getId()).isPresent()) {
            throw new DataNotFoundException(String.format(USER_ID_NOT_FOUND, userRequestDTO.getId()));
        }
        User user = modelMapper.map(userRequestDTO, User.class);
        User u = userRepository.save(user);
        UserResponseDTO userResponseDTO = modelMapper.map(u, UserResponseDTO.class);
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO getUser(long id) {
        logger.info("User Service: getUser(): START");
        Optional<User> user=userRepository.findById(id);
        if(!user.isPresent()){
            throw new DataNotFoundException(CANNOT_FIND_USER);
        }
        UserResponseDTO userResponseDTO=modelMapper.map(user.get(),UserResponseDTO.class);
        return userResponseDTO;
    }
}