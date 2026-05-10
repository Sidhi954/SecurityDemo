package com.bank.service;

import com.bank.dto.UserRequestDTO;
import com.bank.dto.UserResponseDTO;
import com.bank.entity.User;
import com.bank.exception.UserNotFoundException;
import com.bank.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO registerUser(UserRequestDTO request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered: " + request.getEmail());
        }

        User user=User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .status(User.UserStatus.ACTIVE)
                .build();

        User savedUser=userRepository.save(user);
        return mapToResponse(savedUser);
    }

    private UserResponseDTO mapToResponse(User savedUser) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(savedUser.getId());
        userResponseDTO.setFullName(savedUser.getFullName());
        userResponseDTO.setEmail(savedUser.getEmail());
        userResponseDTO.setPhone(savedUser.getPhone());
        userResponseDTO.setAddress(savedUser.getAddress());
        userResponseDTO.setDateOfBirth(savedUser.getDateOfBirth());
        userResponseDTO.setStatus(savedUser.getStatus().name());
        return  userResponseDTO;
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user=userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found with id: "+id));
        return mapToResponse(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO request) {
        User user=userRepository.findById((id))
                .orElseThrow(()->new UserNotFoundException("User not found with id: "+id));

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setDateOfBirth(request.getDateOfBirth());

        User updatedUser=userRepository.save(user);
        return mapToResponse(updatedUser);
    }
}
