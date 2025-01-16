package servlets;

import domain.ServiciosExample;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    public static final String USER = "user";

    private ServiciosExample serviciosExample;

    @Inject
    public LoginServlet(ServiciosExample serviciosExample) {
        this.serviciosExample = serviciosExample;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nombre = req.getParameter("nombre");
        String password = req.getParameter("password");

        if (serviciosExample.login(nombre, password)) {
            //session insert
            req.getSession().setAttribute(USER, nombre);

            resp.getWriter().println("Bienvenido " + nombre);
        } else {
            resp.getWriter().println("Usuario o contraseña incorrectos");
        }


    }


}
