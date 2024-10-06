package org.example.demojavafx.domain.modelo;

import lombok.Data;

@Data
public class Mensaje {
    private final String texto;
    private final Grupo grupo;
}
