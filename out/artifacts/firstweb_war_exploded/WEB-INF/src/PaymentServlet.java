import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();


        String s = (String)session.getAttribute("email");
        String p = (String) session.getAttribute("password");

        try{
            Connection conn = DatabaseConn.getConnection();
            Statement st = conn.createStatement();

            String userID = "select id, peanut, username from users where username='"+ s + "' AND password='" + p +"';";
            ResultSet rs = st.executeQuery(userID);
            rs.next();
            int id = rs.getInt("id");
            int peanut = rs.getInt("peanut");
            String name = rs.getString("username");
            //System.out.println(request.getRequestURI());

            if(session.getAttribute("payment")!= null){
                response.setContentType("text/html");
                PrintWriter out=response.getWriter();
                out.println("<script type=\"text/javascript\">");
                String message = payment(id,peanut,name,(int)session.getAttribute("payment"),session);
                out.println("alert('"+message+"');");
                out.println("</script>");
                String referer = request.getHeader("referer");
                response.sendRedirect(referer);
                out.close();
            }

            if (session.getAttribute("earning")!=null){
                response.setContentType("text/html");
                PrintWriter out=response.getWriter();
                out.println("<script type=\"text/javascript\">");
                String message2 = earning(id,peanut,name,(int)session.getAttribute("earning"),session);
                out.println("alert('"+message2+"');");
                out.println("</script>");
                String referer = request.getHeader("referer");
                response.sendRedirect(referer);
                out.close();
            }

        }catch (Exception e){
        }

    }

    private String payment(int id, int peanut, String name, int value, HttpSession session){
        boolean pay = false;
        update2DatabaseCol(id,peanut,name,value,session,pay);
        return value + " peanuts payment is Successful";
    }

    private String earning(int id, int peanut, String name, int value,HttpSession session){
        boolean earn = true;
        update2DatabaseCol(id,peanut,name,value,session,earn);
        return "You earn "+ value + " peanuts more !!";
    }

    private void update2DatabaseCol(int id, int pea, String name, int value, HttpSession session, boolean flag){
        try{
            Connection connection = DatabaseConn.getConnection();
            Statement stmt = connection.createStatement();
            int result;
            if(flag){
                result = pea + value;
            }else{
                result = pea - value;
            }
            String sql = "UPDATE users " + "SET peanut ="+result+" WHERE id in ("+id+")";
            stmt.executeUpdate(sql);
            session.setAttribute("amountPeanut",result);
        }catch (Exception e){
        }

    }
}  