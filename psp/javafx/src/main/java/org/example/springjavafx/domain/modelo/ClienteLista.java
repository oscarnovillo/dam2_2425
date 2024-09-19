package org.example.springjavafx.domain.modelo;

import lombok.Data;

import java.util.List;

@Data
public class ClienteLista {

    private String nombre;
    private List<Cliente> clientes;
}
