package com.bookmydine.user.service;

import com.bookmydine.common.enums.Roles;
import com.bookmydine.user.dto.UserRequest;
import com.bookmydine.user.dto.UserResponse;
import com.bookmydine.user.entity.User;
import com.bookmydine.user.mapper.UserMapper;
import com.bookmydine.user.service.interfaces.IUserService;
import com.bookmydine.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     *
     */
    @Override
    public UserResponse addUser(UserRequest userRequest) {
        User user = UserMapper.toEntity(userRequest);
        userRepository.save(user);
        return UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .role(Roles.valueOf(user.getRole()))
            .build();
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
            .orElseThrow(() -> new IllegalArgumentException("User not found!!!"));
    }
}
