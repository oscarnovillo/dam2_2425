package org.example.examen.ui;


import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import org.example.examen.domain.modelo.LoginUser;
import org.example.examen.domain.modelo.Token;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/login")
public class LoginController {


    private Key key;

    public LoginController(@Qualifier("JWT") Key key) {
        this.key = key;
    }

    @PostMapping
    public ResponseEntity<Token> login(@RequestBody LoginUser user) {
        if (!user.username().equals("ines") || !user.password().equals("ines")) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }

        // generar token
        String jwt = Jwts.builder()
                .setSubject("APROBACION")
                .setIssuer("EL PUTO AMO")
                .setExpiration(Date
                        .from(LocalDateTime.now().plusSeconds(300).atZone(ZoneId.systemDefault())
                                .toInstant()))
                .claim("user", user.username())
                .signWith(key).compact();

        return ResponseEntity.ok(new Token(jwt));
    }



}
