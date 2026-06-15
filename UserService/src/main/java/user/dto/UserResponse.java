package user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import user.common.enums.Roles;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Schema(description = "User response payload")
public class UserResponse {
    private Long id;
    @Schema(description = "User's first name", example = "John", required = true)
    private String firstName;

    @Schema(description = "User's last name", example = "Doe")
    private String lastName;

    private String email;
    private Roles role;
    private String phoneNumber;

}
