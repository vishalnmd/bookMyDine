package com.bookmydine.security.config;

import com.bookmydine.security.filter.AuthTokenFilter;
import com.bookmydine.security.handler.AuthEntryPointJWT;
import com.bookmydine.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthConfiguration {
    private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final CustomUserDetailsService userDetailsService;

    private final AuthEntryPointJWT unauthorizedHandler;

    private final PasswordEncoder passwordEncoder;

    private final AuthTokenFilter authTokenFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // ===== 1. CSRF PROTECTION =====
            // Disabled for JWT-based APIs (no cookies/sessions)
            .csrf(AbstractHttpConfigurer::disable)

            // ===== 2. FORM LOGIN =====
            // Disabled for REST APIs (JWT handles authentication)
            .formLogin(AbstractHttpConfigurer::disable)

            // ===== 3. HTTP BASIC AUTH =====
            // Disabled - using JWT instead
            .httpBasic(AbstractHttpConfigurer::disable)

            // ===== 4. SESSION MANAGEMENT =====
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // ===== 5. URL AUTHORIZATION =====
            .authorizeHttpRequests(auth -> auth
                // Public endpoints - no authentication required
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/signin").permitAll()
                .requestMatchers("/user").permitAll()
                .requestMatchers("/loginUser").permitAll()
                .requestMatchers("/api/auth/**").permitAll()  // Register/Login
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()

                // Admin endpoints - require ADMIN role
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // All other requests require authentication
                .anyRequest().authenticated()
            )

            // ===== 6. HEADERS CONFIGURATION =====
            .headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)  // For H2 Console
            )

            // ===== 7. EXCEPTION HANDLING =====
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(unauthorizedHandler)
                // Optional: Add access denied handler
                // .accessDeniedHandler(accessDeniedHandler)
            )

            // ===== 8. ADD JWT FILTER =====
            .addFilterBefore(authTokenFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        log.info("Creating authentication provider");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
