package com.userservice.user.service;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.userservice.user.common.enums.UserStatus;
import com.userservice.user.common.exceptions.ResourceAlreadyExistsException;
import com.userservice.user.common.exceptions.ResourceAlreadyUpdatedException;
import com.userservice.user.common.exceptions.ResourceNotFoundException;
import com.userservice.user.dto.UserRequest;
import com.userservice.user.dto.UserResponse;
import com.userservice.user.entity.User;
import com.userservice.user.mapper.UserMapper;
import com.userservice.user.repository.UserRepository;
import com.userservice.user.service.interfaces.IUserService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    @Override
    public UserResponse addUser(UserRequest userRequest) {
        User user = UserMapper.toEntity(userRequest);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(String.format("Email already exists : %s", userRequest.getEmail()));
        }
        return UserMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers(Pageable pageable) {
        List<UserResponse> userResponses = new ArrayList<>();
        List<User> users = userRepository.findAll(pageable).getContent();
        for (User user : users) {
            userResponses.add(UserMapper.toResponse(user));
        }
        return userResponses;
    }

    @Override
    public List<UserResponse> getAllUsers(UserStatus userStatus, Pageable pageable) {
        List<UserResponse> userResponses = new ArrayList<>();
        List<User> users = userRepository.findAllByStatus(userStatus, pageable);
        for (User user : users) {
            userResponses.add(UserMapper.toResponse(user));
        }
        return userResponses;
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
            .map(UserMapper::toResponse)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("User not found for id : %d", id)));
    }

    @Override
    public UserResponse updateUserById(long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
            .map(u -> UserMapper.updatedUser(u, userRequest))
            .orElseThrow(() -> new ResourceNotFoundException(String.format("User not found for id : %d", id)));

        userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse deleteUserById(long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("User not found for id : %d", id)));

        if (user.getStatus() == UserStatus.DELETED) {
            throw new ResourceAlreadyUpdatedException(String.format("User with id %d is already deleted", id));
        }

        // update user status = Deleted
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);

        return UserMapper.toResponse(user);
    }

}
