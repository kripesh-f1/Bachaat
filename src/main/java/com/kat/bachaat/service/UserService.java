package com.kat.bachaat.service;

import com.kat.bachaat.model.User;
import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> getUsers();
}
