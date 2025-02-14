package org.example.backendspring.ui.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import org.example.backendspring.components.MailComponent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class LoginController {


    private final MailComponent mailComponent;

    public LoginController(MailComponent mailComponent) {
        this.mailComponent = mailComponent;
    }

    @GetMapping("/plantilla")
    public String plantilla(Model model) {
        model.addAttribute("nombre", "Oscar");
        return "plantilla";
    }

    // getmapping for login
    @GetMapping("/login")
    @Validated
    public String login(HttpSession session,@RequestParam @NotBlank String nombre
            , Model model) {

        Integer num = (Integer)session.getAttribute("num");

        if (num == null) {
            num = 0;
        }
        num++;
        session.setAttribute("num", num);



        model.addAttribute("nombre", num);
        return "hola";
    }


    // post mapping for login
    @GetMapping("/l")

    public String logn(@SessionAttribute("num") Integer num,Model model) {

        byte []salt = new byte[16];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(salt);
        String codigo = Base64.getUrlEncoder().encodeToString(salt);

        mailComponent.sendMail("oscar.novillo@gmail.com", "hola","<html><a href=\"http://localhost:8080/activar?nombre="+codigo+"\"></a><html>");
        model.addAttribute("nombre", num);
        return "hola";
    }

    @GetMapping("/activar")
    public String activar(@RequestParam String nombre) {
        //activaria el usuario
        return "hola";
    }



}
