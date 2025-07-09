package com.uca.parcialfinalncapas.service.impl;

import com.uca.parcialfinalncapas.dto.request.LoginRequest;
import com.uca.parcialfinalncapas.security.JwtTokenProvider;
import com.uca.parcialfinalncapas.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    AuthenticationManager authenticationManager;
    JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginRequest loginRequest) {
        // Authenticates the user using the provided username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), // User's username
                        loginRequest.getPassword()  // User's password
                )
        );

        // Sets the authentication in Spring Security's context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generates a JWT token for the authenticated user
        String token = jwtTokenProvider.generateToken(authentication);

        // Returns the generated token
        return token;
    }
}
