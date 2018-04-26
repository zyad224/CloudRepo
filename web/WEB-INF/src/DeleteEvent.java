import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Servlet implementation class createEvent
 */
@WebServlet("/DeleteEvent")
public class DeleteEvent extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String String = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteEvent() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int eventID=Integer.parseInt(request.getParameter("Id"));
        deleteEvent(eventID);
        PrintWriter out=response.getWriter();
        out.print("<script language='javascript'>alert('Event has been deleted!');window.location.href='ShowMyEvents.jsp';</script>");    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void deleteEvent(int eventID) {

        Connection con = null;
        try {
            con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();
            String query = "DELETE FROM events where id = '" + eventID +"';";
            statement.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

