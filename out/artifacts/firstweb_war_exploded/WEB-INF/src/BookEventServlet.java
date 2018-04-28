import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

        HttpSession session = request.getSession();
        int eventID = Integer.parseInt(request.getParameter("Id"));
        int userID =(int)request.getSession().getAttribute("userID");
        System.out.println(eventID +","+ userID + " Click the book button");

        //try to book event
        int r = bookEvent(eventID, userID,session);
        if(r == 0){
            PrintWriter out=response.getWriter();
            out.print("<script language='javascript'>" +
                    "alert('You booked an Event and your payment is successful !');" +
                    "window.location.href='EventManagement.jsp';" +
                    "</script>");
        }else if(r == 1){
            PrintWriter out=response.getWriter();
            out.print("<script language='javascript'>" +
                    "alert('You already booked this Event!');" +
                    "window.location.href='EventManagement.jsp';" +
                    "</script>");
        }else if(r == 2){
            PrintWriter out=response.getWriter();
            out.print("<script language='javascript'>" +
                    "alert('You peanuts are not enough for this Event!');" +
                    "window.location.href='EventManagement.jsp';" +
                    "</script>");
        }else if(r == 3){
            PrintWriter out=response.getWriter();
            out.print("<script language='javascript'>" +
                    "alert('You can not attend this Event!');" +
                    "window.location.href='EventManagement.jsp';" +
                    "</script>");
        }else{
            PrintWriter out=response.getWriter();
            out.print("<script language='javascript'>" +
                    "alert('we could not to service to you, Sorry !!');" +
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

    private int bookEvent(int eventID, int userID,HttpSession session) {
        int flag = -1;
        Connection con = null;
        try {
            con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();

            //check user's booking
            String query = "SELECT eventid FROM booking where userid='" + userID +"';";
            ResultSet rs = statement.executeQuery(query);

            List<Integer> list = new ArrayList<Integer>();

            // checking if ResultSet is empty
            if (rs.next() == false)
            {
                flag = bookAndUpdateTable(con,userID,eventID,session);
                System.out.println("This is first time booking of this user" );
                return flag;

            }else{
                do{
                    int qResult = Integer.parseInt(rs.getString("eventid"));
                    System.out.println("qresult: " + qResult + "event id: " + eventID);
                    list.add(qResult);
                } while (rs.next());
            }

            if(!list.contains(eventID)){
                System.out.println(eventID + ", Event is not exist" );
                flag = bookAndUpdateTable(con,userID,eventID,session);
            }else{
                flag = 1;
                System.out.println(eventID + ", Event is exist" );
                }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }

    private int bookAndUpdateTable(Connection con, int userID, int eventID,HttpSession session){
        int flag = -1;
        try{

            con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();

            //get user data from users table
            String query = "SELECT peanut,firstname, lastname FROM users where id='" + userID +"';";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            String fname = rs.getString("firstname");
            String lname = rs.getString("lastname");
            String peanut = rs.getString("peanut");

            //get event data from events table
            query = "SELECT eventName,place,date,time,peopleToAttend,price FROM events where id='" + eventID +"';";
            rs = statement.executeQuery(query);
            rs.next();
            String ename = rs.getString("eventName");
            String people2Attend = rs.getString("peopleToAttend");
            String place = rs.getString("place");
            String date = rs.getString("date");
            String time = rs.getString("time");
            String price = rs.getString("price");

            boolean priceSuitable = false;
            boolean peopleSuitable = false;

            //Check people to attend
            if(Integer.parseInt(people2Attend) > 0){
                peopleSuitable = true;
            }else{
                return 3;
            }

            //check amount of peanut
            if(Integer.parseInt(peanut) > Integer.parseInt(price)){
                priceSuitable = true;
            }else{
                return 2;
            }

            if(priceSuitable && peopleSuitable){

                //Update events table to peopleToAttend col
                int result = Integer.parseInt(people2Attend)-1;
                query = "UPDATE events " + "SET peopleToAttend ="+String.valueOf(result)+" WHERE id in ("+eventID+")";
                statement.executeUpdate(query);

                //make payment
                if(PaymentSystem.doPayment(userID,price,peanut,session)){
                    flag = 0;
                }

                //Insert into booking table
                query = "insert into booking (userid, firstname, lastname, eventid, eventname,place,date,time) " +
                        "Values ('" + userID+ "','" + fname +"','" + lname+ "','" +
                        eventID + "',  '" + ename + "','"+place+"','"+date+"','"+time+"');";
                statement.executeUpdate(query);
                flag = 0;
            }

        }catch (Exception e){
        }
        return flag;
    }
}

