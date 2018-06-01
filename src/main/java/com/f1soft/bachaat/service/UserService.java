package com.f1soft.bachaat.service;

import com.f1soft.bachaat.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User addUser(User user);

    void deleteUser(long id);

    List<User> getUsers(Pageable pageable);

    User updateUser(User user);
}
