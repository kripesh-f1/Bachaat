package com.kat.bachaat.service.impl;

import com.kat.bachaat.dao.RoleRepository;
import com.kat.bachaat.dao.UserRepository;
import com.kat.bachaat.exception.DataNotFoundException;
import com.kat.bachaat.model.User;
import com.kat.bachaat.service.UserService;
import com.kat.bachaat.util.ActivationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    public static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public List<User> getUsers() {
        logger.info("fetch getUser method");
        List<User> userList = userRepository.findAll();
        if (userList.size() == 0 || userList == null) {
            throw new DataNotFoundException("cannot find users.");
        }
        return userList;
    }

    @Override
    public User addUser(User user) {
        logger.info("Inside add User method of User Service.");
        user.setActivationCode(activationCodeUtil.getActivationCode());
        User user1 = userRepository.findByMobileNumber(user.getMobileNumber());
        if (user1 == null) {
            user.setRoles(Arrays.asList(roleRepository.findByName("USER")));
            return userRepository.save(user);
        } else return null;
    }
}
