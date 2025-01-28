package org.example.backendspring.ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {


    @PostMapping("/pruebapost")
    public String post(@RequestParam String nombre, Model model) {
        model.addAttribute("nombre", nombre);


        return "hola";
    }
}
