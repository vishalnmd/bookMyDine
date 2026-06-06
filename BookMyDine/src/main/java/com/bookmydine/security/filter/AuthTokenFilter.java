package com.bookmydine.security.filter;

import java.io.IOException;

import com.bookmydine.security.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtUtils;

	@Autowired
	private UserDetailsService userDetailsService;

	private final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		logger.debug("AuthTokenFilter called for URI : {}",request.getRequestURI());

		try {
			String jwt = parseJwt(request);
			if(jwt!=null && jwtUtils.validateJWTToken(jwt)) {
				String username = jwtUtils.getUsernameFromJWTToken(jwt);

                //need to update this logic
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());

				logger.debug("Roles from JWT :{}",userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request) );

				SecurityContextHolder.getContext().setAuthentication(authentication	);
			}
		} catch (Exception e) {
			logger.error("cannot set User Authentication :{}",e);
		}

		filterChain.doFilter(request, response);

	}

	private String parseJwt(HttpServletRequest request) {
		String jwt = jwtUtils.getJwtFromHeader(request);
		logger.debug("AuthTokenFilter.java: {} ",jwt);

		return jwt;
	}
}
