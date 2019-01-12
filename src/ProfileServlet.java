import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class ProfileServlet extends HttpServlet {
    DataBase dataBase= new DataBase();
    Logger logger = Logger.getLogger(LoginServlet.class.getName());

    @Override
    public void init(){
        try {
            dataBase.readFile();
        }
        catch (IOException e){
            logger.info(e.getMessage());

        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        HttpSession session = request.getSession(false);
        String name = (String)session.getAttribute("name");
        out.print("<p>Name: " + name + "</p><p>Score: " + 0 + "</p>");
        request.getRequestDispatcher("profile.html").include(request, response);

        out.println("</html></body>");
        out.close();


    }
}
