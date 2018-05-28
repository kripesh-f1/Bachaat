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
    public boolean deleteUser(long id) {
        try {
            logger.info("Delete user with id: " + id);
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new DataNotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public List<User> getUsers() {
        logger.info("fetch users from getUsers()");
        List<User> userList = userRepository.findAll();
        if (userList.size() == 0 || userList == null) {
            throw new DataNotFoundException("Cannot find users.");
        }
        return userList;
    }
}
