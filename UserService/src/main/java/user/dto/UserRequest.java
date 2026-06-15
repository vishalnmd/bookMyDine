package user.dto;


import lombok.*;
import user.common.enums.Roles;
import user.common.validation.Password;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String firstName;

    private String lastName;

    private String email;

    @Password
    private String password;

    private Roles role;

    private String phoneNumber;
}
