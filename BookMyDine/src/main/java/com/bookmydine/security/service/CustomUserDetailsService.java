package com.bookmydine.security.service;

import com.bookmydine.auth.dto.UserAuthResponse;
import com.bookmydine.security.principal.UserPrincipal;
import com.bookmydine.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAuthResponse user = userService.getUserByEmail(email);

        return new UserPrincipal(user);
    }
}
