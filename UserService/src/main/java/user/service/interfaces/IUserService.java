package user.service.interfaces;

import org.springframework.data.domain.Pageable;
import user.common.enums.UserStatus;
import user.dto.UserRequest;
import user.dto.UserResponse;

import java.util.List;

public interface IUserService {

    UserResponse addUser(UserRequest userRequest);

    List<UserResponse> getAllUsers(Pageable pageable);

    List<UserResponse> getAllUsers( UserStatus userStatus, Pageable pageable);

    UserResponse getUserById(Long id);

    UserResponse updateUserById(long id, UserRequest userRequest);

    UserResponse deleteUserById(long id);
}
