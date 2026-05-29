package com.bookmydine.user.service.interfaces;

import com.bookmydine.user.dto.UserRequest;
import com.bookmydine.user.dto.UserResponse;

import java.util.List;

public interface IUserService {

    UserResponse addUser(UserRequest userRequest);

    List<UserResponse> getAllUsers();
}
