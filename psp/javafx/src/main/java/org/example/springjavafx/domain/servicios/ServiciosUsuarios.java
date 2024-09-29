package org.example.springjavafx.domain.servicios;

import org.example.springjavafx.domain.modelo.Usuario;
import org.springframework.stereotype.Service;

@Service
public class ServiciosUsuarios {


    public String getNombreUsuario() {
        return "Usuario";
    }

    public String login(Usuario usuario){
        return "";
    }

    public String cargarUsuarios() { return "";}
}
