package org.example.examen.ui;

import org.apache.coyote.Response;
import org.example.examen.domain.modelo.Raton;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RatonesController {

    private static final List<Raton> RATONES = new ArrayList(List.of(
            new Raton("Raton1"),
            new Raton("Raton2"),
            new Raton("Raton3")
    ));

    @GetMapping("/ratones")
    public ResponseEntity<List<Raton>> getRatones() {
        return ResponseEntity.ok().body(RATONES);
    }

    @PostMapping("/ratones")
    public ResponseEntity<Raton> addRaton(@RequestBody Raton raton) {
        RATONES.add(raton);
        return ResponseEntity.ok().body(raton);
    }

}
