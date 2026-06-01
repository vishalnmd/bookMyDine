package com.bookmydine.user.controller;

import com.bookmydine.common.dto.ApiResponse;
import com.bookmydine.user.dto.UserRequest;
import com.bookmydine.user.dto.UserResponse;
import com.bookmydine.user.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Users", description = "APIs for managing users")
public class UserController {
    public static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final IUserService userService;

    @PostMapping("/user")
    @Operation(summary = "Create a new user", description = "Register a new user in the system")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "User created successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "409",
            description = "Email already exists"
        )
    })
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
    @Operation(summary = "Get all users", description = "Retrieve list of all registered users")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Users retrieved successfully"
        )
    })
    public ResponseEntity<?> getAllUsers() {
        LOG.info("Start getAllUsers");
        List<UserResponse> userResponseList = userService.getAllUsers();
        ApiResponse<List<UserResponse>> response = ApiResponse.<List<UserResponse>>builder()
            .message(String.format("Successfully retrieved %d users", userResponseList.size()))
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

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable @NotNull(message = "Invalid Id") Long id) {
        LOG.info("Start deleteUserById:{}", id);
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
            .message("User successfully deleted")
            .status(HttpStatus.OK)
            .timestamp(LocalDateTime.now())
            .data(userService.deleteUserById(id))
            .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/users/active")
    public ResponseEntity<?> getAllActiveUsers() {
        LOG.info("Start getAllActiveUsers");
        List<UserResponse> userResponseList = userService.getAllActiveUsers();
        ApiResponse<List<UserResponse>> response = ApiResponse.<List<UserResponse>>builder()
            .message(String.format("Successfully retrieved %d users", userResponseList.size()))
            .status(HttpStatus.OK)
            .timestamp(LocalDateTime.now())
            .data(userResponseList)
            .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
