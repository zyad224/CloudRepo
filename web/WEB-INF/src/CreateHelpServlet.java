import ApplicationInfo.AppNames;
import DatabaseConnection.DatabaseConn;
import PeanutPaymentSystem.PaymentSystem;

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

        if(session == null){
            PrintWriter out=response.getWriter();
            out.print("<script language='javascript'>" +
                    "alert('Please Login for creating a Help Request!');" +
                    "window.location.href='EventManagement.jsp';" +
                    "</script>");
        }else{
            // int eventID = Integer.parseInt(request.getParameter("Id"));
            int userID =(int)request.getSession().getAttribute("userID");
            // System.out.println(+ userID + " Click the create button");

            String helpName = request.getParameter("Title");
            String helpPlace=request.getParameter("Place");
            String helpDate=request.getParameter("Date");
            String helpPrice= request.getParameter("Price");
            String helpTime= request.getParameter("Time");
            String helpTopic= request.getParameter("Topic");
            String helpDesc= request.getParameter("Description");
            String helpMob= request.getParameter("Mobile");
            String people = request.getParameter("People");

            if(insertHelp(helpName,helpPlace,helpDate,helpPrice, helpTime,helpTopic,helpDesc,helpMob,people,session,userID))
                doGet(request, response);
            else{
                PrintWriter out=response.getWriter();
                out.print("<script language='javascript'>" +
                        "alert('You peanuts are not enough for creating a Help Request!');" +
                        "window.location.href='EventManagement.jsp';" +
                        "</script>");
            }
        }
    }

    protected void createDynamicPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String path = "Peer2peer.jsp";
        PrintWriter output = response.getWriter();
        output.println("<!DOCTYPE html>\n" + "<html>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"CSS\\eventList.css\">" + "<body>");
        output.println("<center><h2>You create a help request !!</h2></center>");
        output.println("<p></br></br><center> " + "<a href="+path+" class=\"button\" >Click here to back</a></center></p>");
        output.println("</body>" + "</html>\n");
    }

    private boolean insertHelp(String helpName, String helpPlace, String helpDate, String helpPrice
            , String helpTime,String helpTopic,String helpDesc,String helpMob, String people, HttpSession session, int userID) {

        boolean flag=false;
        Connection con = null;

        try {
            con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();
            String query2 = "SELECT peanut,firstname, lastname,usertype FROM users where id='" + userID +"';";

            ResultSet rs = statement.executeQuery(query2);
            rs.next();
            String peanut = rs.getString("peanut");
            String userType = rs.getString("usertype");

            if(Integer.parseInt(peanut)>=5) {

                String query = "insert into help (helpName, place, date, time, topic, description,mobile,price,peopleToAttend, userID) " +
                        "Values ('" + helpName + "','" + helpPlace + "','" + helpDate + "', '" +
                        helpTime + "',  '" + helpTopic + "', '" + helpDesc + "', '" + helpMob + "','" + helpPrice + "', '" + people + "' , " +
                        "'" + session.getAttribute("userID") + "');";

                statement.executeUpdate(query);
                PaymentSystem.doPayment(userID,"",peanut,userType, AppNames.Peer2Peer,session);
                flag=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}