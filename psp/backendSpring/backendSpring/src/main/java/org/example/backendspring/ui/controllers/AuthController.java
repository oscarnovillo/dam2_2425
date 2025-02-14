package org.example.backendspring.ui.controllers;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.backendspring.ui.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {

        Authentication auth =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(auth);
        request.getSession().setAttribute("LOGIN", auth);


        return jwtService.generateToken(userDetailsService.loadUserByUsername(username));
    }

    @GetMapping("/loginBasic")
    public String loginBasic(Principal principal, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        request.getSession().setAttribute("LOGIN", auth);

        return principal.getName();
    }



}
