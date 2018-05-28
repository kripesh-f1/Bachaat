package com.f1soft.bachaat.service.impl;

import com.f1soft.bachaat.service.UserService;
import com.f1soft.bachaat.repository.UserRepository;
import com.f1soft.bachaat.exception.DataNotFoundException;
import com.f1soft.bachaat.entity.User;
import com.f1soft.bachaat.repository.RoleRepository;
import com.f1soft.bachaat.utils.ActivationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    public boolean deleteUser(long id) {
        logger.info("Delete user with id: " + id);
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new DataNotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public List<User> getUsers() {
        logger.info("Fetch users from getUsers()");
        List<User> userList = userRepository.findAll();
        if (userList.size() == 0 || userList == null) {
            throw new DataNotFoundException("Cannot find users.");
        }
        return userList;
    }

    @Override
    public User updateUser(User user) {
        logger.info("Inside Update User Service");
        User currentUser = userRepository.save(user);
        if (currentUser == null) {
            throw new DataNotFoundException("Cannot find user.");
        }
        return currentUser;
    }

    @Override
    public User addUser(User user) {
        logger.info("Inside add User method of User Service.");
        user.setActivationCode(activationCodeUtil.getActivationCode());
        User u = userRepository.findByMobileNumber(user.getMobileNumber());
        if (u == null) {
            user.setRoles(Arrays.asList(roleRepository.findByName("USER")));
            return userRepository.save(user);
        } else return null;
    }
}
