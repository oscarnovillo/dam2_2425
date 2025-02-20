package org.example.backendspring.ui.controllers;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/loginToken")
public class TokenController {


    private Key key;

    public TokenController(@Qualifier("JWT") Key key) {
        this.key = key;
    }

    @GetMapping
    public String getToken(@RequestParam String nombre,@RequestParam String password){

    if (nombre.equals("oscar") && password.equals("1234")) {


        // generar token
        // generar token
        String jwt = Jwts.builder()
                .setSubject("APROBACION")
                .setIssuer("EL PUTO AMO")
                .setExpiration(Date
                        .from(LocalDateTime.now().plusSeconds(300).atZone(ZoneId.systemDefault())
                                .toInstant()))
                .claim("user", nombre)
                .signWith(key).compact();

        return jwt;
    }
    else
        return "error";
    }



    @GetMapping("/validate")
    public String validateToken(@RequestParam String token){

        Jws<Claims> jwsV = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);



        return (String)jwsV.getBody().get("user");
    }
}
