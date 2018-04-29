import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
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

        HttpSession session = request.getSession();
        int helpID = Integer.parseInt(request.getParameter("Id"));
        int userID =(int)request.getSession().getAttribute("userID");

        int r = checkUserAlreadyRequested(helpID, userID,session);
        if(r == 0){
            PrintWriter out=response.getWriter();
            out.print("<script language='javascript'>" +
                    "alert('You requested a Help!');" +
                    "window.location.href='Peer2peer.jsp';" +
                    "</script>");
        }else if(r == 1){
            PrintWriter out=response.getWriter();
            out.print("<script language='javascript'>" +
                    "alert('You already requested this Help!');" +
                    "window.location.href='Peer2peer.jsp';" +
                    "</script>");
        }else if(r == 2){
            PrintWriter out=response.getWriter();
            out.print("<script language='javascript'>" +
                    "alert('Your peanuts are not enough for this request!');" +
                    "window.location.href='Peer2peer.jsp';" +
                    "</script>");
        }else if(r == 3){
            PrintWriter out=response.getWriter();
            out.print("<script language='javascript'>" +
                    "alert('You can not request this Help because it is full!');" +
                    "window.location.href='Peer2peer.jsp';" +
                    "</script>");
        }else{
            PrintWriter out=response.getWriter();
            out.print("<script language='javascript'>" +
                    "alert('we could not to service to you, Sorry !!');" +
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

    private int checkUserAlreadyRequested(int helpID, int userID, HttpSession session){
        int flag = -1;
        Connection con = null;
        try {
            con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();

            //check user's booking
            String query = "SELECT helpid FROM bookinghelp where userid='" + userID +"';";
            ResultSet rs = statement.executeQuery(query);

            List<Integer> list = new ArrayList<Integer>();

            // checking if ResultSet is empty
            if (rs.next() == false)
            {
                flag = bookHelp(userID,helpID,session);
                System.out.println("This is the first time requesting of help" );
                return flag;

            }else{
                do{
                    int qResult = rs.getInt("helpid");
                    System.out.println("qresult: " + qResult + "help id: " + helpID);
                    list.add(qResult);
                } while (rs.next());
            }

            if(!list.contains(helpID)){
                System.out.println(helpID + ", Event is not exist" );
                flag = bookHelp(userID,helpID,session);
            }else{
                flag = 1;
                System.out.println(helpID + ", Event is exist" );
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    private int bookHelp(int userID, int helpID, HttpSession session) {
        int flag = -1;
        Connection con = null;

        try {
            con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();

            //get user data from users table
            String query = "SELECT peanut, firstname, lastname,usertype FROM users where id='" + userID +"';";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            String fname = rs.getString("firstname");
            String lname = rs.getString("lastname");
            String peanut = rs.getString("peanut");
            String userType = rs.getString("usertype");

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

            boolean peopleSuitable = false;
            boolean priceSuitable = false;

            //Check people to attend
            if(Integer.parseInt(people2Attend) > 0){
                peopleSuitable = true;
            }else{
                return 3;
            }

            //check amount of peanut
            if(Integer.parseInt(peanut) >= Integer.parseInt(price)){
                priceSuitable = true;
            }else{
                return 2;
            }

            if(priceSuitable && peopleSuitable){
                //Update events table to peopleToAttend col
                int result = Integer.parseInt(people2Attend)-1;
                query = "UPDATE help " + "SET peopleToAttend ="+String.valueOf(result)+" WHERE id in ("+helpID+")";
                statement.executeUpdate(query);

                //make payment
                if(PaymentSystem.doPayment(userID,price,peanut,userType,"Peer2Peer",session)){
                    flag = 0;
                }

                //Insert into booking table
                query = "insert into bookinghelp (userid, firstname, lastname, helpid, helpname,place,date,time,topic,mobile,price) " +
                        "Values ('" + userID+ "','" + fname +"','" + lname+ "','" +
                        helpID + "',  '" + hname + "','"+place+"','"+date+"','"+time+"','"+topic+"','"+mobile+"','"+price+"');";
                statement.executeUpdate(query);
                flag = 0;
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}