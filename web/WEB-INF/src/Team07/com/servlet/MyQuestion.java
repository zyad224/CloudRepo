package Team07.com.servlet;

import Team07.com.user.Question;
import Team07.com.userdao.QuestionReplyDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/MyQuestion")
public class MyQuestion extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session=request.getSession();
        if(session != null){
            int userID = (Integer) session.getAttribute("userID");
            System.out.println("MyQuestion" + userID);
            QuestionReplyDao qr = new QuestionReplyDao();
            ArrayList<Question> list =  (ArrayList<Question>) qr.queryAllMyQuestion(userID);
            request.setAttribute("myQuestionList",list);
            request.getRequestDispatcher("myProblems.jsp").forward(request, response);
        }else{
            response.setContentType("text/html");
            String path = "login.jsp";
            PrintWriter output = response.getWriter();
            output.println("<!DOCTYPE html>\n" + "<html>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"CSS\\eventList.css\">" + "<body>");
            output.println("<center><h2> You haven't log in,please log in first !!</h2></center>");
            output.println("<p></br></br><center> " + "<a href="+path+" class=\"button\" >Click here to back</a></center></p>");
            output.println("</body>" + "</html>\n");
        }
    }
}
