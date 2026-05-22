package com.bookmydine.user.service;

import com.bookmydine.common.enums.Roles;
import com.bookmydine.user.dto.UserRequest;
import com.bookmydine.user.dto.UserResponse;
import com.bookmydine.user.entity.User;
import com.bookmydine.user.mapper.UserMapper;
import com.bookmydine.user.repository.IUserService;
import com.bookmydine.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

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
}
