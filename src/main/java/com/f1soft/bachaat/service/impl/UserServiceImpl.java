package com.f1soft.bachaat.service.impl;

import com.f1soft.bachaat.service.UserService;

import com.f1soft.bachaat.repository.UserRepository;
import com.f1soft.bachaat.exception.DataNotFoundException;
import com.f1soft.bachaat.entity.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        logger.info("fetch getUser method");
        List<User> userList = userRepository.findAll();
        if (userList.size() == 0 || userList == null) {
            throw new DataNotFoundException("cannot find users.");
        }
        return userList;
    }
}
