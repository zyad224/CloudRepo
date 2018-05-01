import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.ResultSet;
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

        HttpSession session = request.getSession();
        // int eventID = Integer.parseInt(request.getParameter("Id"));
        int userID = (int) request.getSession().getAttribute("userID");
        boolean flag = false;
        Connection con = null;

        try {
            con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();
            String query2 = "SELECT peanut,firstname, lastname,usertype FROM users where id='" + userID + "';";
            ResultSet rs = statement.executeQuery(query2);
            rs.next();
            System.out.println("innn");
            String peanut = rs.getString("peanut");
            String userType = rs.getString("usertype");
            System.out.println("user del p:" + peanut);
            System.out.println("user del type:" + userType);

            if (Integer.parseInt(peanut) >= 5) {
                // doGet(request, response);
                int eventID=Integer.parseInt(request.getParameter("Id"));
               deleteEvent(eventID);
               PrintWriter out=response.getWriter();
                out.print("<script language='javascript'>alert('Study Group has been deleted!');window.location.href='ShowMyEvents.jsp';</script>");
                PaymentSystem.doPayment(userID, "", peanut, userType, AppNames.EventStudy, session);


            } else {
                PrintWriter out = response.getWriter();
                out.print("<script language='javascript'>" +
                        "alert('You peanuts are not enough for deleting Study Group!');" +
                        "window.location.href='EventManagement.jsp';" +
                        "</script>");
            }
        } catch (Exception e) {

        }


    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


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

