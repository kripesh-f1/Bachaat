package com.kat.bachaat.service.impl;

import com.kat.bachaat.dao.UserRepository;
import com.kat.bachaat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean deleteUser(long id) {
       userRepository.deleteById(id);
       return true;
    }
}
