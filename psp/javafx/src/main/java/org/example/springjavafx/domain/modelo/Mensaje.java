
package org.example.springjavafx.domain.modelo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;


@Data
public class Mensaje {

    private final String texto;
    private final LocalDateTime timestamp;
    private final Usuario owner;
    private final ArrayList<Usuario> destinatarios;
    private final String grupo;


}
