package com.kat.bachaat.service.impl;

import com.kat.bachaat.dao.UserRepository;
import com.kat.bachaat.exception.DataNotFoundException;
import com.kat.bachaat.model.User;
import com.kat.bachaat.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;


@Service
@Transactional
public class UserServiceImpl implements UserService
{

    private  static Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        logger.info("entered getAll method");
        List<User> userList=userRepository.findAll();
        if(userList.size()==0 || userList==null){
            throw new DataNotFoundException("cannot find users.");
        }
        return userList;
    }
}
