import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class RegistrationServlet extends HttpServlet {
    DataBase dataBase= new DataBase();
    Logger logger = Logger.getLogger(RegistrationServlet.class.getName());

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
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        String registration= request.getParameter("registration");
        if(registration!=null){
            if(!dataBase.checkName(name)) {
                dataBase.addData(name, password);
//                out.println("Welcome, " + name);
                request.getRequestDispatcher("/login.html").forward(request, response);
            }
            else{
                request.getRequestDispatcher("/errorAlreadyExist.html").forward(request, response);
            }
        }
        out.println("</html></body>");
        out.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
