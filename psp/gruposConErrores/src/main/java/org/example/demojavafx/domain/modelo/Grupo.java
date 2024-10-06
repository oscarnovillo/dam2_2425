package org.example.demojavafx.domain.modelo;

import lombok.Data;

import java.util.List;

@Data
public class Grupo {

    private final String nombre;
    private final String password;
    private final List<User> usuarios;

}
