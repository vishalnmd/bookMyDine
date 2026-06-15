package com.userservice.user.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.userservice.user.common.config.ApiResponse;
import com.userservice.user.common.enums.UserStatus;
import com.userservice.user.dto.UserRequest;
import com.userservice.user.dto.UserResponse;
import com.userservice.user.service.interfaces.IUserService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    public static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody UserRequest request) {
        UserResponse userResponse = userService.addUser(request);
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .message("User Created Successfully")
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now())
                .data(userResponse)
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false, defaultValue = "1") int pageNumber, @RequestParam(required = false) UserStatus status) {
        LOG.info("Start getAllUsers");
        pageSize = Math.max(pageSize, 1000);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<UserResponse> userResponseList = userService.getAllUsers(status, pageable);
        ApiResponse<List<UserResponse>> response = ApiResponse.<List<UserResponse>>builder()
                .message(String.format("Successfully retrieved %d users", userResponseList.size()))
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .data(userResponseList)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
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

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable @NotNull(message = "Invalid Id") Long id, @RequestBody UserRequest request) {
        LOG.info("Start updateUserById:{}", id);
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .message("User successfully updated")
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .data(userService.updateUserById(id, request))
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
