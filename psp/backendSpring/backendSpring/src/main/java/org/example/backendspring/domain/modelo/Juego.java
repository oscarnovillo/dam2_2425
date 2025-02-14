package org.example.backendspring.domain.modelo;

import lombok.Data;

import java.util.List;


@Data
public class Juego{

    private String nombre;
    private List<Jugador> jugadores;


}
