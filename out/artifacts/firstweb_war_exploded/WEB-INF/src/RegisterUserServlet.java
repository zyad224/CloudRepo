import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class LoginCheck
 */
@WebServlet("/RegisterUserServlet")
public class RegisterUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUserServlet() {
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
        //response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.getWriter().append("Served at: ").append(request.getContextPath());
        User newUser = new User();

        newUser.ID = newUser.hashCode()/1000;
        newUser.peanut = 1000;
        newUser.username = request.getParameter("uname");
        newUser.password = request.getParameter("password");
        newUser.password2 = request.getParameter("password2");
        newUser.firstname = request.getParameter("fname");
        newUser.lastname = request.getParameter("lname");
        newUser.gender = request.getParameter("gender");


        //if((!newUser.username.isEmpty()) && (!newUser.password.isEmpty())){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            int value = userInfoCorrect(newUser);
            if(value == 0) {
                if (insertData2Database(newUser)) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Registration is successful..');");
                    out.println("location='index.jsp';");
                    out.println("</script>");
                } else {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Registration is not successful!!!');");
                    out.println("location='index.jsp';");
                    out.println("</script>");
                    }
                }
                if(value == 1){
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Enter an username');");
                    out.println("location='registerUser.jsp';");
                    out.println("</script>");
                }
                if(value == 2){
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Enter a password');");
                    out.println("location='registerUser.jsp';");
                    out.println("</script>");
                }
                if(value == 3){
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Password confirmation is wrong!');");
                    out.println("location='registerUser.jsp';");
                    out.println("</script>");
                }
                if(value == 4){
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Enter a First Name');");
                    out.println("location='registerUser.jsp';");
                    out.println("</script>");
                }
                if(value == 5){
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Enter a Last Name');");
                    out.println("location='registerUser.jsp';");
                    out.println("</script>");
                }
                if(value == 6){
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Choose a gender');");
                    out.println("location='registerUser.jsp';");
                    out.println("</script>");
                }
        //}
    }

    private int userInfoCorrect(User newUser) {
        if (newUser.username == null || newUser.username.equals("")) {
            return 1;
        }
        if (newUser.password == null || newUser.password.equals("")) {
            return 2;
        }
        if (!newUser.password.equals(newUser.password2)){
            return 3;
        }
        if(newUser.firstname == null || newUser.firstname.equals("")){
            return 4;
        }
        if(newUser.lastname == null || newUser.lastname.equals("")){
            return 5;
        }
        if(newUser.gender == null || newUser.gender.equals("")){
            return 6;
        }
        return 0;
    }

    private boolean insertData2Database(User newUser){

        Connection conn = null;
        Statement stmt = null;
        boolean status = false;
        try{
            conn = DatabaseConn.getConnection();
            stmt = conn.createStatement();

            String sql = "insert into users (peanut, username, password, firstname, lastname, gender) " +
                    "Values ('" + newUser.peanut + "','" + newUser.username +"','" + newUser.password + "', '" +
                    newUser.firstname + "',  '" + newUser.lastname + "', '" + newUser.gender + "');";
            stmt.executeUpdate(sql);
            status = true;
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        return status;
    }
}

class User{
    public int ID;
    public int peanut;
    public String username;
    public String password;
    public String password2;
    public String firstname;
    public String lastname;
    public String gender;
}
