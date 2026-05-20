package com.bookmydine.user.dto;

import com.bookmydine.common.enums.Roles;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Roles role;
}
