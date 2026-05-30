package com.bookmydine.user.controller;

import com.bookmydine.common.dto.ApiResponse;
import com.bookmydine.user.dto.UserRequest;
import com.bookmydine.user.dto.UserResponse;
import com.bookmydine.user.service.interfaces.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    public static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final IUserService userService;

    @PostMapping("/user")
    public ResponseEntity<?> addUser(
        @Valid @RequestBody UserRequest request) {
        UserResponse userResponse = userService.addUser(request);
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
            .message("User Created Successfully")
            .status(HttpStatus.CREATED)
            .timestamp(LocalDateTime.now())
            .data(userResponse)
            .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        LOG.info("Start getAllUsers");
        List<UserResponse> userResponseList = userService.getAllUsers();
        ApiResponse<List<UserResponse>> response = ApiResponse.<List<UserResponse>>builder()
            .message(String.format("Successfully retrieved %d users",userResponseList.size()))
            .status(HttpStatus.OK)
            .timestamp(LocalDateTime.now())
            .data(userResponseList)
            .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable @NotNull(message = "Invalid Id") Long id) {
        LOG.info("Start getUserById:{}", id);
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
            .message("User Found Successfully")
            .status(HttpStatus.OK)
            .timestamp(LocalDateTime.now())
            .data(userService.getUserById(id))
            .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable @NotNull(message = "Invalid Id") Long id, @RequestBody UserRequest request) {
        LOG.info("Start updateUser:{}", id);
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
            .message("User successfully updated")
            .status(HttpStatus.OK)
            .timestamp(LocalDateTime.now())
            .data(userService.updateUser(id, request))
            .build();
        return  ResponseEntity.status(response.getStatus()).body(response);
    }

}
