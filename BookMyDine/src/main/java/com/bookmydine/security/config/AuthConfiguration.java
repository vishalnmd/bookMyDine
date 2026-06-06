package com.bookmydine.security.config;

import com.bookmydine.security.filter.AuthTokenFilter;
import com.bookmydine.security.handler.AuthEntryPointJWT;
import com.bookmydine.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class AuthConfiguration {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private AuthEntryPointJWT unauthorizedHandler;

	@Bean
    AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
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
                .requestMatchers("/loginUser").permitAll()
                .requestMatchers("/api/auth/**").permitAll()  // Register/Login
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/error").permitAll()

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
            .addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
	}

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration config,
        PasswordEncoder passwordEncoder,
        UserDetailsService userDetailsService) throws Exception {

        // Modern way using AuthenticationManagerBuilder
        return config.getAuthenticationManager();
    }

    @Bean
	public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder(18);
	}

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
    	return builder.getAuthenticationManager();
    	}
}
