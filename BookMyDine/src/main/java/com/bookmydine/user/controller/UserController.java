package com.bookmydine.user.controller;

import com.bookmydine.common.dto.ApiResponse;
import com.bookmydine.user.dto.UserRequest;
import com.bookmydine.user.dto.UserResponse;
import com.bookmydine.user.repository.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(
        @Valid
        @RequestBody UserRequest request
    ) {
        UserResponse userResponse = userService.addUser(request);
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
            .message("User Created Successfully")
            .status(HttpStatus.OK.value())
            .timestamp(LocalDateTime.now())
            .data(userResponse)
            .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
