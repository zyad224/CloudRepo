import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Servlet implementation class BookEventServlet
 */
@WebServlet("/BookEventServlet")
public class BookEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String String = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookEventServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int eventID = Integer.parseInt(request.getParameter("Id"));
        int userID =(int)request.getSession().getAttribute("userID");

        if(bookEvent(eventID, userID)){
            PrintWriter out=response.getWriter();
            out.print("<script language='javascript'>" +
                    "alert('You booked an Event!');" +
                    "window.location.href='EventManagement.jsp';" +
                    "</script>");
        }
        }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private boolean bookEvent(int eventID, int userID) {
        boolean flag = false;
        Connection con = null;
        try {
            con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();

            //get user data from users table
            String query = "SELECT firstname, lastname FROM users where id='" + userID +"';";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            String fname = rs.getString("firstname");
            String lname = rs.getString("lastname");

            //get event data from events table
            query = "SELECT eventName,peopleToAttend FROM events where id='" + eventID +"';";
            rs = statement.executeQuery(query);
            rs.next();
            String ename = rs.getString("eventName");
            String people2Attend = rs.getString("peopleToAttend");

            //Update events table to peopleToAttend col
            int result = Integer.parseInt(people2Attend)-1;
            query = "UPDATE events " + "SET peopleToAttend ="+String.valueOf(result)+" WHERE id in ("+eventID+")";
            statement.executeUpdate(query);

            //Insert into booking table
            query = "insert into booking (userid, firstname, lastname, eventid, eventname) " +
                    "Values ('" + userID+ "','" + fname +"','" + lname+ "','" +
                    eventID + "',  '" + ename + "');";
            statement.executeUpdate(query);
            flag = true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}

