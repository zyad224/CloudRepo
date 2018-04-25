
import org.omg.CORBA.Request;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.sql.*;


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
        HttpSession session = request.getSession(false);

        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        //if (session.getAttribute("email") == null || session.getAttribute("email").equals("")) {
            if (checkUserFromDB(email, pass, session)) {
                session.setAttribute("email", email);
                session.setAttribute("password",pass);
                response.sendRedirect("login.jsp");
            } else
                response.sendRedirect("loginError.jsp");
        /*}
        else{
            response.setContentType("text/html");
            PrintWriter output = response.getWriter();
            output.println("<!DOCTYPE html>\n" + "<html>\n" + "<body>");
            output.println("<h2>This session is open !!</h2>");
            output.println("</body>" + "</html>\n");
        }*/
        /*
        if (session.getAttribute("email") == null || session.getAttribute("email").equals("")){
            if(checkUserFromDB(email, pass)) {
                session.setAttribute("email",email);
                response.sendRedirect("login.jsp");
            }else
                response.sendRedirect("loginError.jsp");
        }else{
            response.setContentType("text/html");
            PrintWriter output = response.getWriter();
            output.println("<!DOCTYPE html>\n" + "<html>\n" + "<body>");
            output.println("<h2>This session is open !!</h2>");
            output.println("</body>" + "</html>\n");
        }*/
    }
/*
    private boolean checkXml(String email, String pass) {
        boolean flag = false;
        try {

            File file = new File("C:\\Users\\pegas\\IdeaProjects\\firstweb\\web\\users.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            String usr = document.getElementsByTagName("user").item(0).getTextContent();
            String pwd = document.getElementsByTagName("password").item(0).getTextContent();

            if (email.equals(usr) && pass.equals(pwd)) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
*/
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
