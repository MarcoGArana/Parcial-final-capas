package com.uca.parcialfinalncapas.service.impl;

import com.uca.parcialfinalncapas.dto.request.UserCreateRequest;
import com.uca.parcialfinalncapas.dto.request.UserUpdateRequest;
import com.uca.parcialfinalncapas.dto.response.UserResponse;
import com.uca.parcialfinalncapas.entities.Role;
import com.uca.parcialfinalncapas.entities.Ticket;
import com.uca.parcialfinalncapas.entities.User;
import com.uca.parcialfinalncapas.exceptions.UserNotFoundException;
import com.uca.parcialfinalncapas.repository.UserRepository;
import com.uca.parcialfinalncapas.service.UserService;
import com.uca.parcialfinalncapas.utils.enums.Rol;
import com.uca.parcialfinalncapas.utils.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponse findByCorreo(String correo) {
        return UserMapper.toDTO(userRepository.findByCorreo(correo)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con correo: " + correo)));
    }

    @Override
    public UserResponse save(UserCreateRequest user) {

        if (userRepository.findByCorreo(user.getCorreo()).isPresent()) {
            throw new UserNotFoundException("Ya existe un usuario con el correo: " + user.getCorreo());
        }

        List<Role> roles = List.of(Role.builder()
                .id(Rol.valueOf(user.getNombreRol()).ordinal())
                .name(user.getNombreRol())
                .build());

        return UserMapper.toDTO(userRepository.save(UserMapper.toEntityCreate(user, roles)));
    }

    @Override
    public UserResponse update(UserUpdateRequest user) {
        Optional<User> usuario = userRepository.findById(user.getId());
        if (usuario.isEmpty()) {
            throw new UserNotFoundException("No se encontró un usuario con el ID: " + user.getId());
        }

        user.setTickets(usuario.get().getTickets());

        return UserMapper.toDTO(userRepository.save(UserMapper.toEntityUpdate(user)));
    }

    @Override
    public void delete(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException("No se encontró un usuario con el ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> findAll() {
        return UserMapper.toDTOList(userRepository.findAll());
    }

    @Override
    public List<Ticket> findByUsernameOrEmail(String usernameOrEmail) {
        User usuario = userRepository.findByNombreOrCorreo(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con username o email: " + usernameOrEmail));
        return usuario.getTickets();
    }
}
