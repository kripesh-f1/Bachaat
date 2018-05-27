package com.kat.bachaat.service.impl;

import com.kat.bachaat.dao.RoleRepository;
import com.kat.bachaat.dao.UserRepository;
import com.kat.bachaat.exception.UserAlreadyExistsException;
import com.kat.bachaat.model.User;
import com.kat.bachaat.service.UserService;
import com.kat.bachaat.util.ActivationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ActivationCodeUtil activationCodeUtil;

    public static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public User addUser(User user) {
        logger.info("Inside add User method of User Service.");
        user.setActivationCode(activationCodeUtil.getActivationCode());
        User user1 = userRepository.findByMobileNumber(user.getMobileNumber());
        if (user1 == null) {
            user.setRoles(Arrays.asList(roleRepository.findByName("USER")));
            return userRepository.save(user);
        }
        else return null;
    }
}
