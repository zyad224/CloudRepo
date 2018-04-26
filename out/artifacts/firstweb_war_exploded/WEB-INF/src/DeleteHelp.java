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
@WebServlet("/DeleteHelp")
public class DeleteHelp extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String String = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteHelp() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int helpID=Integer.parseInt(request.getParameter("Id"));
        deleteHelp(helpID);
        PrintWriter out=response.getWriter();
        out.print("<script language='javascript'>alert('Help request has been deleted!');window.location.href='ShowMyHelps.jsp';</script>");    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        HttpSession session = request.getSession();
//
//        String eventName = request.getParameter("Title");
//        String eventPlace=request.getParameter("Place");
//        String eventDate=request.getParameter("Date");
//        String eventPrice= request.getParameter("Price");
//        String eventTime= request.getParameter("Time");
//        String people= request.getParameter("People to attend");
//
//        insertEvent(eventName,eventPlace,eventDate,eventPrice, eventTime,people,session);
        doGet(request, response);
    }



    private void deleteHelp(int helpID) {

        Connection con = null;

        try {
            con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();
            String query = "DELETE FROM help where id = '" + helpID +"';";
            statement.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

