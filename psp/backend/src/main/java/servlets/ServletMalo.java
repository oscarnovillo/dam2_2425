package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ServletMalo", value = "/ServletMalo")
public class ServletMalo extends HttpServlet {

    // NO SE PONE ESTADO NUNCA
    private int i = 1;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer contador;
        if (req.getSession().getAttribute("contador") == null) {
            req.getSession().setAttribute("contador", 0);
        }

        contador = (Integer)req.getSession().getAttribute("contador");

        resp.getWriter().println(contador);
        contador ++;
        req.getSession().setAttribute("contador", contador);



    }
}
