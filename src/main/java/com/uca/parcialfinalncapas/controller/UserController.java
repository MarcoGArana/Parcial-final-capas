package com.uca.parcialfinalncapas.controller;

import com.uca.parcialfinalncapas.dto.request.UserCreateRequest;
import com.uca.parcialfinalncapas.dto.request.UserUpdateRequest;
import com.uca.parcialfinalncapas.dto.response.GeneralResponse;
import com.uca.parcialfinalncapas.dto.response.UserResponse;
import com.uca.parcialfinalncapas.entities.Ticket;
import com.uca.parcialfinalncapas.entities.User;
import com.uca.parcialfinalncapas.security.JwtTokenProvider;
import com.uca.parcialfinalncapas.service.UserService;
import com.uca.parcialfinalncapas.utils.ResponseBuilderUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/all")
    @PreAuthorize("hasRole('TECH')")
    public ResponseEntity<GeneralResponse> getAllUsers(@RequestHeader("Authorization") String authHeader) {
        List<UserResponse> users = userService.findAll();

        return ResponseBuilderUtil.buildResponse(
                "Usuarios obtenidos correctamente",
                users.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK,
                users
        );
    }

    @GetMapping("/tickets")
    @PreAuthorize("hasRole('TECH')")
    public ResponseEntity<GeneralResponse> getUserTickets(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String usernameOrEmail = jwtTokenProvider.getUsernameFromToken(token);
        List<Ticket> tickets = userService.findByUsernameOrEmail(usernameOrEmail);
        return ResponseBuilderUtil.buildResponse("Tickets encontrados", HttpStatus.OK, tickets);
    }

    @GetMapping("/{correo}")
    @PreAuthorize("hasRole('TECH')")
    public ResponseEntity<GeneralResponse> getUserByCorreo(@PathVariable String correo, @RequestHeader("Authorization") String authHeader) {
        UserResponse user = userService.findByCorreo(correo);
        return ResponseBuilderUtil.buildResponse("Usuario encontrado", HttpStatus.OK, user);
    }

    @PostMapping
    @PreAuthorize("hasRole('TECH')")
    public ResponseEntity<GeneralResponse> createUser(@Valid @RequestBody UserCreateRequest user) {
        UserResponse createdUser = userService.save(user);
        return ResponseBuilderUtil.buildResponse("Usuario creado correctamente", HttpStatus.CREATED, createdUser);
    }

    @PutMapping
    @PreAuthorize("hasRole('TECH')")
    public ResponseEntity<GeneralResponse> updateUser(@Valid @RequestBody UserUpdateRequest user, @RequestHeader("Authorization") String authHeader) {
        UserResponse updatedUser = userService.update(user);
        return ResponseBuilderUtil.buildResponse("Usuario actualizado correctamente", HttpStatus.OK, updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TECH')")
    public ResponseEntity<GeneralResponse> deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        userService.delete(id);
        return ResponseBuilderUtil.buildResponse("Usuario eliminado correctamente", HttpStatus.OK, null);
    }
}
