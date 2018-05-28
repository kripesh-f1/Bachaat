package com.f1soft.bachaat.service;

import com.f1soft.bachaat.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> getUsers();
}
