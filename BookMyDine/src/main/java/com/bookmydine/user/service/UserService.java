package com.bookmydine.user.service;

import com.bookmydine.common.exception.ResourceAlreadyExistsException;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
    public UserResponse updateUser(long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
            .map(u -> UserMapper.updatedUser(u, userRequest))
            .orElseThrow(() -> new ResourceNotFoundException(String.format("User not found for id : %d", id)));

        userRepository.save(user);

        return UserMapper.toResponse(user);
    }
}
