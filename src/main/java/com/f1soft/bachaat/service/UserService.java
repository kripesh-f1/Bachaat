package com.f1soft.bachaat.service;

import com.f1soft.bachaat.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    boolean deleteUser(long id);
    List<User> getUsers();

}
