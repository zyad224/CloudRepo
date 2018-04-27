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
@WebServlet("/BookHelpServlet")
public class BookHelpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String String = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookHelpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int helpID = Integer.parseInt(request.getParameter("Id"));
        int userID =(int)request.getSession().getAttribute("userID");

        if(bookHelp(helpID, userID)){
            PrintWriter out=response.getWriter();
            out.print("<script language='javascript'>" +
                    "alert('You booked a Help Request!');" +
                    "window.location.href='Peer2peer.jsp';" +
                    "</script>");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private boolean bookHelp(int helpID, int userID) {
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

            //get help data from events table
            query = "SELECT helpName,place,date,time,topic,mobile,price,peopleToAttend FROM help where id='" + helpID +"';";
            rs = statement.executeQuery(query);
            rs.next();
            String hname = rs.getString("helpName");
            String topic = rs.getString("topic");
            String price = rs.getString("price");
            String mobile = rs.getString("mobile");
            String place= rs.getString("place");
            String date= rs.getString("date");
            String time= rs.getString("time");
            String people2Attend= rs.getString("peopleToAttend");





            //Update events table to peopleToAttend col
            int result = Integer.parseInt(people2Attend)-1;
            query = "UPDATE help " + "SET peopleToAttend ="+String.valueOf(result)+" WHERE id in ("+helpID+")";
            statement.executeUpdate(query);

            //Insert into booking table
            query = "insert into bookinghelp (userid, firstname, lastname, helpid, helpname,place,date,time,topic,mobile,price) " +
                    "Values ('" + userID+ "','" + fname +"','" + lname+ "','" +
                    helpID + "',  '" + hname + "','"+place+"','"+date+"','"+time+"','"+topic+"','"+mobile+"','"+price+"');";
            statement.executeUpdate(query);
            flag = true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}