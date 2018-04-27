import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class LoginCheck
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Create Session
        HttpSession session = request.getSession();

        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        if(SessionTableUtil.checkSessionTable(email,pass)){
            System.out.println("session is already opened");
            response.setContentType("text/html");
            String path = "/EventManagement.jsp";
            PrintWriter output = response.getWriter();
            output.println("<!DOCTYPE html>\n" + "<html>\n" + "<body>");
            output.println("<h2>This session is already open !!</h2>");
            output.println("</body>" + "</html>\n");
        }else{
            System.out.println("new session required");
            if (checkUserFromDB(email, pass, session)) {
                session.setAttribute("email", email);
                session.setAttribute("password", pass);
                List<String> user = getUserInfo(email,pass);
                SessionTableUtil.add2SessionTable(user.get(0),email,session.getId(),user.get(2),pass,user.get(1));
                request.getRequestDispatcher("login.jsp").forward(request, response);
                //response.sendRedirect("login.jsp");
            } else {
                request.getRequestDispatcher("loginError.jsp").forward(request, response);
                //response.sendRedirect("loginError.jsp");
            }
        }
    }

    private boolean checkUserFromDB(String uName, String pass,  HttpSession session) {
        boolean flag = false;
        PreparedStatement pt = null; // manages prepared statement
        Connection con = null; // manages connection
        try {

            con = DatabaseConn.getConnection();
            pt = con.prepareStatement("select id,peanut,username,password from students.users where username=?");

            // process query results
            pt.setString(1, uName);
            ResultSet rs = pt.executeQuery();
            String orgUname = "", orPass = "";
            int currentPeanut=0;
            int userId=0;
            while (rs.next()) {
                orgUname = rs.getString("username");
                orPass = rs.getString("password");
                currentPeanut = rs.getInt("peanut");
                userId = rs.getInt("id");
            }
            if (orgUname.equals(uName)&&orPass.equals(pass)) {
                flag = true;
                session.setAttribute("userID",userId);
                session.setAttribute("amountPeanut",currentPeanut);
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }



    private List<String> getUserInfo(String username, String pass){
        List<String> data = new ArrayList<String>();
        try{
            Connection conn = DatabaseConn.getConnection();
            Statement st = conn.createStatement();
            String userID = "select id, firstname, lastname from users where username='"+ username + "' AND password='" + pass +"';";
            ResultSet rs = st.executeQuery(userID);
            rs.next();
            int id = rs.getInt("id");
            String fname = rs.getString("firstname");
            String lname = rs.getString("lastname");
            data.add(String.valueOf(id));
            data.add(fname);
            data.add(lname);
        }catch (Exception e){
        }
        return data;
    }
}
