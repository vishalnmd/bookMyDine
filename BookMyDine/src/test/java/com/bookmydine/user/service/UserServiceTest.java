package com.bookmydine.user.service;

import com.bookmydine.common.enums.Roles;
import com.bookmydine.common.enums.UserStatus;
import com.bookmydine.common.exception.ResourceAlreadyExistsException;
import com.bookmydine.common.exception.ResourceNotFoundException;
import com.bookmydine.user.dto.UserRequest;
import com.bookmydine.user.dto.UserResponse;
import com.bookmydine.user.entity.User;
import com.bookmydine.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .id(1L)
            .firstName("Pomu")
            .lastName("deshmukh")
            .email("Pomu420@gmail.com")
            .password("Pomu420")
            .role(Roles.USER)
            .status(UserStatus.ACTIVE)
            .build();

        userRequest = UserRequest.builder()
            .firstName("Pomu")
            .lastName("deshmukh")
            .email("Pomu420@gmail.com")
            .role(Roles.USER)
            .build();
    }

    @Test
    void getUserById_whenUserExists_thenReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse response = userService.getUserById(1L);

        System.out.println(response);

        assertEquals(1L, response.getId());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_whenUserDoesNotExist_throwException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
            () -> userService.getUserById(99L));
    }

    @Test
    void addUser_thenReturnUser() {
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        UserResponse response = userService.addUser(userRequest);
        verify(userRepository).save(any(User.class));

        System.out.println(response);

        assertEquals(1L, response.getId());
    }

    @Test
    void addUser_whenDublicateEmailFound_throwException() {
        when(userRepository.save(any()))
            .thenThrow(new DataIntegrityViolationException("Duplicate email"));

        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class,
            () -> userService.addUser(userRequest));

        // ✅ Verify repository was still called
        verify(userRepository, times(1)).save(any());

        System.out.println(exception.getMessage());

        assertEquals("Email already exists : Pomu420@gmail.com", exception.getMessage());
    }

    @Test
    void updateUser_whenUserExists_thenReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userRequest.setFirstName("Pomu_Harshit");
        UserResponse response = userService.updateUserById(1L, userRequest);

        System.out.println(response);
        assertEquals("Pomu_Harshit", response.getFirstName());
    }

    @Test
    void updateUser_whenUserNotExists_thenThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
            () -> userService.updateUserById(1L, userRequest));

        verify(userRepository, times(1)).findById(1L);
    }

}
