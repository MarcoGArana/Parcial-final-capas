package com.uca.parcialfinalncapas.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuth implements AuthenticationEntryPoint {

    //This method sends an HTTP 401 (Unauthorized) error when an authentication exception occurs,
    //indicating that the user is not authenticated or the provided token is invalid.
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        response.getWriter().write("{\"error\": \"Token no proveido o token invalido\"}");
    }
}
