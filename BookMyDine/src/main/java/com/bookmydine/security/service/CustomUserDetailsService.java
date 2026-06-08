package com.bookmydine.security.service;

import com.bookmydine.auth.dto.UserAuthResponse;
import com.bookmydine.security.principal.UserPrincipal;
import com.bookmydine.user.service.UserService;
import com.bookmydine.user.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAuthResponse user = userService.getUserByEmail(email);
        log.info("user found in customerUserDetailsService: {}", user);
        return new UserPrincipal(user);
    }
}
