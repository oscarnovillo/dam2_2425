package org.example.springjavafx.ui.listado;


import lombok.Data;

import java.util.List;

@Data
public class ListadoState {

    private final List<String> cromos;
    private final String error;
    private final boolean cargando;
}
