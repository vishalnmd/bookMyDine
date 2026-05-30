package com.bookmydine.user.entity;

import com.bookmydine.common.entity.BaseEntity;
import com.bookmydine.common.enums.Roles;
import com.bookmydine.common.enums.UserStatus;
import com.bookmydine.common.validation.Password;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotNull(message = "First Name is required")
    @NotBlank(message = "First Name is required")
    private String firstName;

    private String lastName;

    @Column(unique = true, updatable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Password
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Roles role;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;
}
