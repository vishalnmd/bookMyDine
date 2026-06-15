package com.userservice.user.service.interfaces;

import com.userservice.user.dto.OwnerRestaurantResponse;
import org.springframework.data.domain.Pageable;
import com.userservice.user.common.enums.UserStatus;
import com.userservice.user.dto.UserRequest;
import com.userservice.user.dto.UserResponse;

import java.util.List;

public interface IUserService {

    UserResponse addUser(UserRequest userRequest);

    List<UserResponse> getAllUsers(Pageable pageable);

    List<UserResponse> getAllUsers( UserStatus userStatus, Pageable pageable);

    UserResponse getUserById(Long id);

    UserResponse updateUserById(long id, UserRequest userRequest);

    UserResponse deleteUserById(long id);

    OwnerRestaurantResponse getOwnerRestaurantById(long id);
}
