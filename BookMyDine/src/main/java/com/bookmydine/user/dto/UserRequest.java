package com.bookmydine.user.dto;

import com.bookmydine.common.enums.Roles;
import com.bookmydine.common.validation.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "User registration/update request payload")
public class UserRequest {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    @Schema(description = "User's first name", example = "John", required = true)
    private String firstName;

    @Schema(description = "User's last name", example = "Doe")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Password
    private String password;

    @NotNull(message = "Role is required")
    @Schema(description = "User's role", example = "CUSTOMER", required = true)
    private Roles role;

    private String phoneNumber;
}
