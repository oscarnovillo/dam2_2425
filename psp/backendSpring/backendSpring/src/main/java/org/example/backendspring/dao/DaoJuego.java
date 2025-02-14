package org.example.backendspring.dao;

import org.example.backendspring.errors.NotFoundException;
import org.example.backendspring.domain.modelo.Juego;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DaoJuego {



    public Juego updateJuego(Juego juego) {
        if (new Random().nextBoolean()) {
            return juego;
        }
        else {
            throw new NotFoundException("No se ha encontrado el juego");
        }
    }
}
