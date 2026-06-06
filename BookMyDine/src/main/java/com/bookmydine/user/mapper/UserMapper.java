package com.bookmydine.user.mapper;

import com.bookmydine.auth.dto.UserAuthResponse;
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

    public static User updatedUser(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName()!=null ? userRequest.getFirstName() : user.getFirstName());
        user.setLastName(userRequest.getLastName()!=null ? userRequest.getLastName() : user.getLastName());
        user.setRole(userRequest.getRole()!=null ? userRequest.getRole() : user.getRole());
        user.setPhoneNumber(userRequest.getPhoneNumber()!=null ? userRequest.getPhoneNumber() : user.getPhoneNumber());

        return user;
    }

    public static UserAuthResponse toUserAuthResponse(User user){
        return UserAuthResponse.builder()
            .email(user.getEmail())
            .password(user.getPassword())
            .role(user.getRole())
            .build();
    }
}
