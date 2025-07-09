package com.uca.parcialfinalncapas.dto.request;

import com.uca.parcialfinalncapas.entities.Ticket;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserUpdateRequest {
    private Long id;
    private String nombre;
    private String password;
    private String nombreRol;
    private List<Ticket> tickets;
}
