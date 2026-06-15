package com.userservice.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.userservice.user.common.enums.UserStatus;
import com.userservice.user.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByStatus(UserStatus userStatus, Pageable pageable);

    Optional<User> findByEmail(String email);
}
