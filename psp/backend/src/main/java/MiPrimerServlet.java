import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Random;


@WebServlet("/hola")
public class MiPrimerServlet extends HttpServlet {





    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchRequest(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchRequest(req,resp);
    }

    private void dispatchRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        Integer vecesi = (Integer)req.getSession().getAttribute("veces");
        Integer adi =  adi = (Integer)req.getSession().getAttribute("adi");
        if (vecesi == null) {
            vecesi = 0;
        }
        if (adi==null)
        {
            Random r = new Random();
            adi = r.nextInt();
            req.getSession().setAttribute("adi",adi);
        }

       vecesi += 1;

        req.getSession().setAttribute("veces",vecesi);
        var nombre = req.getParameter("nombre");
        if (nombre == null) {
            nombre = "Mundo";
        }
        int numero = Integer.parseInt(req.getParameter("num"));

        resp.getWriter().println("<html><body><h1>"+vecesi+" "+adi+"Hola "+nombre + " "+numero+"</h1></body></html>");
    }
}
