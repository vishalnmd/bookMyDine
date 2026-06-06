package com.bookmydine.user.service.interfaces;

import com.bookmydine.auth.dto.UserAuthResponse;
import com.bookmydine.user.dto.UserRequest;
import com.bookmydine.user.dto.UserResponse;

import java.util.List;

public interface IUserService {

    UserResponse addUser(UserRequest userRequest);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUserById(long id, UserRequest userRequest);

    UserResponse deleteUserById(long id);

    List<UserResponse> getAllActiveUsers();

    UserAuthResponse getUserByEmail(String email);

}
