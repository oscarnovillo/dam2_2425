package org.example.examen.domain.modelo;

import java.util.List;

public record Alumno(
        String nombre,
        String apellido,
        String dni,
        String email,
        List<Asignatura> asignaturas
) {
}
