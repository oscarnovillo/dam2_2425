package org.example.backendspring.ui;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import org.example.backendspring.components.MailComponent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class LoginController {


    private final MailComponent mailComponent;

    public LoginController(MailComponent mailComponent) {
        this.mailComponent = mailComponent;
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
       // mailComponent.sendMail("oscar.novillo@gmail.com", "hola","hola");
        model.addAttribute("nombre", num);
        return "hola";
    }


}
