package org.example.backendspring.ui.controllers;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.example.backendspring.domain.servicios.ServiciosJuegos;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.aot.hint.TypeReference.listOf;

@RestController
@RequestMapping("/api/juego")
public class JuegoRestController {


    private final ServiciosJuegos serviciosJuegos;

    public JuegoRestController(ServiciosJuegos serviciosJuegos) {
        this.serviciosJuegos = serviciosJuegos;
    }

    @GetMapping("")
    public List<Juego> getJuego(){
        Juego juego = new Juego();
        juego.setNombre("Juego de prueba");
        return List.of(juego);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Juego> getJuego(@PathVariable Long id){
        Juego juego = new Juego();
        juego.setNombre("Juego de prueba "+id);
        return ResponseEntity.ok().body(juego);
    }

    @PostMapping
    public ResponseEntity<Juego> createJuego(@RequestBody Juego juego){

        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(juego);

    }

    @PutMapping
    public ResponseEntity<Juego> updateJuego(@RequestBody Juego juego){
        Juego juegoActualizado  = serviciosJuegos.updateJuego(juego);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(juegoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateJuego(@PathVariable Long id){
        // en caso de ok  ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).build();
        return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
    }

}
