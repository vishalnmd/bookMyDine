package com.bookmydine.user.repository;

import com.bookmydine.user.dto.UserRequest;
import com.bookmydine.user.dto.UserResponse;

public interface IUserService {

    UserResponse addUser(UserRequest userRequest);
}
