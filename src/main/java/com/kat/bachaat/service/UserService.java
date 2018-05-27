package com.kat.bachaat.service;

import com.kat.bachaat.model.User;

import java.util.List;

public interface UserService {

    boolean deleteUser(long id);

    List<User> getUsers();

}
