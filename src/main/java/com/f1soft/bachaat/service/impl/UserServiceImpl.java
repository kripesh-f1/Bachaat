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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.f1soft.bachaat.utils.MessageConstant.*;

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
            throw new DataNotFoundException(String.format(USER_NOT_FOUND, id));
        }
    }

    @Override
    public List<UserResponseDTO> getUsers(Pageable pageable) {
        logger.info("User Service: getUsers(): START");
        Page<User> userPage = userRepository.findAll(pageable);
        if (userPage == null) {
            throw new DataNotFoundException(CAN_NOT_FIND_USERS);
        }

        List<User> userList = userPage.getContent();
        List<UserResponseDTO> userResponseDTOS = userList.stream()
                .map(UserResponseDTO::new).collect(Collectors.toList());

        return userResponseDTOS;
    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) {
        logger.info("User Service: updateUser(): START");
        if (userRequestDTO.getId() == null) {
            throw new DataNotFoundException(ID_CAN_NOT_BE_NULL);
        }
        if (!userRepository.findById(userRequestDTO.getId()).isPresent()) {
            throw new DataNotFoundException(String.format(USER_NOT_FOUND, userRequestDTO.getId()));
        }
        User user = modelMapper.map(userRequestDTO, User.class);
        User u = userRepository.save(user);
        UserResponseDTO userResponseDTO = modelMapper.map(u, UserResponseDTO.class);
        return userResponseDTO;
    }
}
