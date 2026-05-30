package com.bookmydine.user.mapper;

import com.bookmydine.user.dto.UserRequest;
import com.bookmydine.user.dto.UserResponse;
import com.bookmydine.user.entity.User;

public class UserMapper {

    public static User toEntity(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        return user;
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName() != null ? user.getLastName() : "")
            .email(user.getEmail())
            .role(user.getRole())
            .phoneNumber(user.getPhoneNumber() != null ? user.getPhoneNumber() : "")
            .build();
    }
}
