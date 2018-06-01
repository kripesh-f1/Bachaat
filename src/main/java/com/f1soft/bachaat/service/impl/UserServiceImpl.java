package com.f1soft.bachaat.service.impl;

import com.f1soft.bachaat.entity.User;
import com.f1soft.bachaat.exception.DataNotFoundException;
import com.f1soft.bachaat.exception.UserAlreadyExistsException;
import com.f1soft.bachaat.repository.RoleRepository;
import com.f1soft.bachaat.repository.UserRepository;
import com.f1soft.bachaat.service.UserService;
import com.f1soft.bachaat.utils.ActivationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ActivationCodeUtil activationCodeUtil;

    private static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

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
    public List<User> getUsers(Pageable pageable) {
        logger.info("Inside Get Users Service");
        Page<User> userPage = userRepository.findAll(pageable);
        List<User> userList=userPage.getContent();
        if (userList == null||userList.size()==0) {
            throw new DataNotFoundException("Cannot find users.");
        }

        return userList;
    }

    @Override
    public User updateUser(User user) {
        logger.info("Inside Update User Service");
        if (user.getId() == null) {
            throw new DataNotFoundException("User id can not be null");
        }
        if (!userRepository.findById(user.getId()).isPresent()) {
            throw new DataNotFoundException(String.format("User with %d cannot be found",user.getId()));
        }

        return userRepository.save(user);
    }

    @Override
    public User addUser(User user) {
        logger.info("Inside Add User Service");
        User u = userRepository.findByMobileNumber(user.getMobileNumber());
        if (u != null) {
            throw new UserAlreadyExistsException("User with " + u.getMobileNumber() + " mobile number already exist.");
        }
        user.setActivationCode(activationCodeUtil.getActivationCode());
        user.setRoles(Arrays.asList(roleRepository.findByName("USER")));
        return userRepository.save(user);
    }
}
