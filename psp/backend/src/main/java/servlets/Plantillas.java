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

@WebServlet(name = "Plantillas", value = "/privado/Plantillas")
public class Plantillas extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //check login en session
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }




        Equipo p = new Equipo( "1", "Madrid", "Madrid", "Santiago Bernabeu");
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ThymeLeafListener.TEMPLATE_ENGINE_ATTR);

        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);

        WebContext context = new WebContext(webExchange);

        String name = request.getParameter("name");
        String template = "home";
        if (name == null || name.isEmpty())
        {
            template = "error";
            context.setVariable("error", "nombre no valido");
        }
        else {
            context.setVariable("name", name);
            context.setVariable("equipo", p);
        }


        templateEngine.process(template, context, response.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
