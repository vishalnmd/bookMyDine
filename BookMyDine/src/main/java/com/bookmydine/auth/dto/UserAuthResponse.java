package com.bookmydine.auth.dto;

import com.bookmydine.common.enums.Roles;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class UserAuthResponse {
    private String email;
    private String password;
    private Roles role;
}
