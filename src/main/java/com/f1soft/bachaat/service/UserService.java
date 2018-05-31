package com.f1soft.bachaat.service;

import com.f1soft.bachaat.dto.request.UserRequestDTO;
import com.f1soft.bachaat.dto.response.UserResponseDTO;
import com.f1soft.bachaat.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponseDTO addUser(UserRequestDTO userRequestDTO);

    void deleteUser(long id);

    List<UserResponseDTO> getUsers();

    UserResponseDTO updateUser(UserRequestDTO userRequestDTO);

}
