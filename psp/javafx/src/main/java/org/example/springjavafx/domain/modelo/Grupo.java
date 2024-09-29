package org.example.springjavafx.domain.modelo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class Grupo {

    private final ArrayList<Usuario> miembros;
    private final String nombre;
    private final String password;
    private final Usuario administrador;
    private final LocalDateTime fechaCreacion;
    private final String fotoPerfil;

}
