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
@WebServlet("/CreateEventServlet")
public class CreateEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String String = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateEventServlet() {
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
                    "alert('Please Login for creating Study Group!');" +
                    "window.location.href='EventManagement.jsp';" +
                    "</script>");
        }else{
            // int eventID = Integer.parseInt(request.getParameter("Id"));
            int userID =(int)request.getSession().getAttribute("userID");
            System.out.println(+ userID + " Click the create button");

            String eventName = request.getParameter("Title");
            String eventPlace=request.getParameter("Place");
            String eventDate=request.getParameter("Date");
            String eventPrice= request.getParameter("Price");
            String eventTime= request.getParameter("Time");
            String people= request.getParameter("People to attend");

            //check
            if(insertEvent(eventName,eventPlace,eventDate,eventPrice, eventTime,people,session,userID))
                doGet(request, response);
            else {
                PrintWriter out=response.getWriter();
                out.print("<script language='javascript'>" +
                        "alert('You peanuts are not enough for creating Study Group!');" +
                        "window.location.href='EventManagement.jsp';" +
                        "</script>");
            }
        }
    }

    protected void createDynamicPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String path = "EventManagement.jsp";
        PrintWriter output = response.getWriter();
        output.println("<!DOCTYPE html>\n" + "<html>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"CSS\\eventList.css\">n" + "<body>");
        output.println("<center><h2>You create an Event !!</h2></center>");
        output.println("<p></br></br><center> " + "<a href="+path+" class=\"button\" >Click here to back</a></center></p>");
        output.println("</body>" + "</html>\n");
    }

    private boolean insertEvent(String eventName, String eventPlace, String eventDate, String eventPrice
            , String eventTime,String people, HttpSession session, int userID) {

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

            System.out.println("pe:"+peanut);
            System.out.println("u:"+userType);


            if(Integer.parseInt(peanut)>=5) {


                String query = "insert into events (eventName, place, date, time, peopleToAttend, price, userID) " +
                        "Values ('" + eventName + "','" + eventPlace + "','" + eventDate + "', '" +
                        eventTime + "',  '" + people + "', '" + eventPrice + "' , " +
                        "'" + session.getAttribute("userID") + "');";
                statement.executeUpdate(query);

                PaymentSystem.doPayment(userID,"",peanut,userType, AppNames.EventStudy,session);
                flag=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }
}

class Event{
    String eventName;
    String place;
    String date;
    String time;
    String peopleToAttend;
    String price;
    int userID;

    public String getEventName() { return eventName; }
    public String getPlace() { return place; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getPeople() { return peopleToAttend; }
    public String getPrice() { return price; }
    public int getID() { return userID; }

    public void setEventName(String eventName) { this.eventName=eventName; }
    public void setPlace(String place) { this.place=place; }
    public void setDate(String date) { this.date=date; }
    public void setTime(String time) { this.time=time; }
    public void setPeople(String peopleToAttend) { this.peopleToAttend=peopleToAttend; }
    public void setPrice(String price) { this.price=price; }
    public void setID(int userID) { this.userID=userID; }

}