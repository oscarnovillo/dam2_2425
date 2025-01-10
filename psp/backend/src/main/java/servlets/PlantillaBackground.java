package servlets;

import domain.Equipo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listeners.ThymeLeafListener;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Juan", value = {"/a", "/b"})
public class PlantillaBackground extends HttpServlet {




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ThymeLeafListener.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);



        String template = "param";
        String color;


        if (req.getParameter("today") != null && !req.getParameter("today").isEmpty()) {
            color = req.getParameter("today");
            req.getSession().setAttribute("color", color);
        }
        else
            color = (String) req.getSession().getAttribute("color");

        String mensaje = req.getParameter("mensaje");
        String sNumero = req.getParameter("numero");
        if (sNumero != null && !sNumero.isEmpty()) {


            try {
                int valor = Integer.parseInt(sNumero);
                context.setVariable("today", color);
                context.setVariable("mensaje", mensaje + valor);
                context.setVariable("numero", valor);
                List<Equipo> equipos = new ArrayList<>();
                equipos.add(new Equipo("1", "Madrid", "Madrid", "Santiago Bernabeu"));
                equipos.add(new Equipo("2", "Barcelona", "Barcelona", "Camp Nou"));
                equipos.add(new Equipo("3", "Valencia", "Valencia", "Mestalla"));
                equipos.add(new Equipo("4", "Sevilla", "Sevilla", "Sanchez Pizjuan"));
                // add 8 equipos more
                equipos.add(new Equipo("5", "Betis", "Sevilla", "Benito Villamarin"));
                equipos.add(new Equipo("6", "Atletico", "Madrid", "Wanda Metropolitano"));
                equipos.add(new Equipo("7", "Villarreal", "Villarreal", "Estadio de la Ceramica"));
                equipos.add(new Equipo("8", "Real Sociedad", "San Sebastian", "Anoeta"));
                equipos.add(new Equipo("9", "Athletic", "Bilbao", "San Mames"));
                equipos.add(new Equipo("10", "Espanyol", "Barcelona", "RCDE Stadium"));

                context.setVariable("equipos", equipos);


            }
            catch (NumberFormatException e) {
                context.setVariable("error", "numero no valido");

                template = "error";
            }

        }
        else {
            context.setVariable("error", "numero no existe");

            template = "error";
        }


        templateEngine.process(template, context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


}
