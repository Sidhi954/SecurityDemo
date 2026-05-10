package com.bank.service;

import com.bank.dto.UserRequestDTO;
import com.bank.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO registerUser(UserRequestDTO request);
    UserResponseDTO getUserById(Long id);
    UserResponseDTO updateUser(Long id, UserRequestDTO request);
}
