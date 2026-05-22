package com.bookmydine.user.dto;

import com.bookmydine.common.enums.Roles;
import com.bookmydine.common.validation.Password;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Password
    private String password;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    private Roles role;
}
