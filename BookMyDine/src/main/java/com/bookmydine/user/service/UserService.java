package com.bookmydine.user.service;

import com.bookmydine.auth.dto.UserAuthResponse;
import com.bookmydine.common.enums.UserStatus;
import com.bookmydine.common.exception.ResourceAlreadyExistsException;
import com.bookmydine.common.exception.ResourceAlreadyUpdatedException;
import com.bookmydine.common.exception.ResourceNotFoundException;
import com.bookmydine.user.dto.UserRequest;
import com.bookmydine.user.dto.UserResponse;
import com.bookmydine.user.entity.User;
import com.bookmydine.user.mapper.UserMapper;
import com.bookmydine.user.repository.UserRepository;
import com.bookmydine.user.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<UserResponse> getAllUsers() {
        List<UserResponse> userResponses = new ArrayList<>();
        List<User> users = userRepository.findAll();
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

    @Override
    public List<UserResponse> getAllActiveUsers() {
        List<UserResponse> userResponses = new ArrayList<>();
        List<User> users = userRepository.findAllByStatus(UserStatus.ACTIVE);
        for (User user : users) {
            userResponses.add(UserMapper.toResponse(user));
        }
        return userResponses;
    }

    @Override
    public UserAuthResponse getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .map(UserMapper::toUserAuthResponse)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("User not found for email : %s", email)));
    }


}
