package org.example.examen.ui;

import jakarta.websocket.server.PathParam;
import org.example.examen.domain.modelo.Alumno;
import org.example.examen.domain.modelo.ApiError;
import org.example.examen.domain.modelo.Asignatura;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/alumnos")
public class AlumnosController {

    @GetMapping()
    public List<Alumno> getAlumnos() {
        return List.of(
                new Alumno("Juan", "Perez", "12345678"
                        , "", List.of(new Asignatura( "Matematicas", "MAT123", 4, 3.5),
                        new Asignatura( "Fisica", "FIS123", 4, 3.5),
                        new Asignatura( "Quimica", "QUI123", 4, 3.5))),

                new Alumno("Maria", "Gomez", "87654321"
                        , "", List.of(new Asignatura( "Fisica", "FIS123", 4, 3.5))),

                new Alumno("Pedro", "Garcia", "45678912"
                        , "", null)





        );



    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlumnos(@PathVariable String id) {
        // delete alumnos
        Random r = new Random();
        if (r.nextInt(100) < 30)
            return ResponseEntity.status(500)
                    .body(new ApiError(40,"No se pudo borrar"));
        return ResponseEntity.noContent().build();
    }
}
