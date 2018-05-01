import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        //request.getRequestDispatcher("/login.jsp").include(request, response);

        HttpSession session = request.getSession();
        if(session != null){
            SessionTableUtil.deleteUserInSessionTable(session.getId());
            session.invalidate();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('You are successfully logged out!');");
            out.println("location='index.jsp';");
            out.println("</script>");
            out.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("email");
        String pass = request.getParameter("password");
        List<String> userData = UserTableUtils.getUserInfo(username,pass);
        SessionTableUtil.deleteUserInSessionTableByID(userData.get(0));
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        out.println("<script type=\"text/javascript\">");
        out.println("alert('You are successfully logged out!');");
        out.println("location='index.jsp';");
        out.println("</script>");
        out.close();
    }
}  