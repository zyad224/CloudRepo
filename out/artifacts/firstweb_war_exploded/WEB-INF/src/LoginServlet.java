import DatabaseConnection.DatabaseConn;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.sql.*;
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
       // response.getWriter().append("Served at: ").append(request.getContextPath());
        request.getRequestDispatcher("/login.jsp").forward(request, response);
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
            if(session.getId().equals(SessionTableUtil.getSessionID(email,pass))){

                response.setContentType("text/html");
                String path = "login.jsp";
                PrintWriter output = response.getWriter();
                output.println("<script type=\"text/javascript\">");
                output.println("alert('Welcome to the same session !!');");
                output.println("</script>");

                request.getRequestDispatcher("login.jsp").forward(request, response);
                System.out.println("same session");
            }else{
                response.setContentType("text/html");
                String path = "index.jsp";
                PrintWriter output = response.getWriter();
                output.println("<!DOCTYPE html>\n" + "<html>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"CSS\\eventList.css\">" + "<body>");
                output.println("<center><h2>This session is already opened! Please log out from previous session!</h2></center>");
                output.println("<p></br></br><center> " + "<a href="+path+" class=\"button\" >Click here to back</a></center></p>");
                output.println("</body>" + "</html>\n");
                System.out.println("opened session, try different browser");
            }
        }else{
            if (checkUserFromDB(email, pass, session)) {
                session.setAttribute("email", email);
                session.setAttribute("password", pass);
                List<String> user = UserTableUtils.getUserInfo(email,pass);
                SessionTableUtil.add2SessionTable(user.get(0),email,session.getId(),user.get(2),pass,user.get(1));
                request.getRequestDispatcher("login.jsp").forward(request, response);
                System.out.println("new session");
            } else {
                System.out.println("not registered user");
                request.getRequestDispatcher("loginError.jsp").forward(request, response);
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
}
