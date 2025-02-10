package org.example.backendspring.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.example.backendspring.dao.DaoJuego;
import org.example.backendspring.errors.UserValidationException;
import org.example.backendspring.ui.controllers.Juego;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;

@RequiredArgsConstructor
@Service
public class ServiciosJuegos {

    private final DaoJuego daoJuego;


    public Juego updateJuego(Juego data) {
        // validaciones

        if (data.getNombre().length() < 3) {
            throw new UserValidationException("El nombre del juego debe tener al menos 3 caracteres");
        }
        return daoJuego.updateJuego(data);
    }
}
