package com.bookmydine.user.controller;

import com.bookmydine.common.dto.ApiResponse;
import com.bookmydine.user.dto.UserRequest;
import com.bookmydine.user.dto.UserResponse;
import com.bookmydine.user.service.interfaces.IUserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class UserController {
    public static final Logger LOG = LoggerFactory.getLogger(UserController.class);

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

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        LOG.info("Start getAllUsers");
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

}
