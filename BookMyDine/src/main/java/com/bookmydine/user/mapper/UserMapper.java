package com.bookmydine.user.mapper;

import com.bookmydine.user.dto.UserRequest;
import com.bookmydine.user.entity.User;

public class UserMapper {

    public static User toEntity(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole().toString());
        return user;
    }
}
