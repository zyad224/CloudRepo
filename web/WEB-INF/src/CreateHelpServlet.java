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
@WebServlet("/CreateHelpServlet")
public class CreateHelpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String String = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateHelpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createDynamicPage(request,response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        String helpName = request.getParameter("Title");
        String helpPlace=request.getParameter("Place");
        String helpDate=request.getParameter("Date");
        String helpPrice= request.getParameter("Price");
        String helpTime= request.getParameter("Time");
        String helpTopic= request.getParameter("Topic");
        String helpDesc= request.getParameter("Description");
        String helpMob= request.getParameter("Mobile");




        insertHelp(helpName,helpPlace,helpDate,helpPrice, helpTime,helpTopic,helpDesc,helpMob,session);
        doGet(request, response);
    }

    protected void createDynamicPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String path = "/Peer2peer.jsp";
        PrintWriter output = response.getWriter();
        output.println("<!DOCTYPE html>\n" + "<html>\n" + "<body>");
        output.println("<h2>You create a help request !!</h2>");
        output.println("<p>This is a dynamic web page </br> " + "<a href="+path+">Click here to back</a></p>");
        output.println("</body>" + "</html>\n");
    }

    private void insertHelp(String helpName, String helpPlace, String helpDate, String helpPrice
            , String helpTime,String helpTopic,String helpDesc,String helpMob, HttpSession session) {

        Connection con = null;

        try {
            con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();
            String query = "insert into help (helpName, place, date, time, topic, description,mobile,price, userID) " +
                    "Values ('" + helpName + "','" + helpPlace +"','" + helpDate + "', '" +
                    helpTime + "',  '" + helpTopic + "', '" + helpDesc + "', '"  + helpMob + "', '" +helpPrice + "' , " +
                    "'" + session.getAttribute("userID") + "');";


//            String query = "insert into events (eventName, place, date, time, peopleToAttend, price, userID) " +
//                    "Values ('" + eventName + "','" + eventPlace +"','" + eventDate + "', '" +
//                    eventTime + "',  '" + people + "', '" + eventPrice + "' , " +
//                    "'" + session.getAttribute("userID") + "');";
            statement.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}