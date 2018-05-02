package Team07.com.servlet;

import ApplicationInfo.AppNames;
import PeanutPaymentSystem.PaymentSystem;
import Team07.com.userdao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/QuestionIndex")
public class QuestionIndex extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        request.setCharacterEncoding("utf-8");
//        response.setCharacterEncoding("utf-8");

        int userID = 0;
        int userPeanut = -1;
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("userID"));

        if(session.getAttribute("userID") == null){
            response.setContentType("text/html");
            String path = "login.jsp";
            PrintWriter output = response.getWriter();
            output.println("<!DOCTYPE html>\n" + "<html>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"CSS\\eventList.css\">" + "<body>");
            output.println("<center><h2> You haven't log in,please log in first !!</h2></center>");
            output.println("<p></br></br><center> " + "<a href="+path+" class=\"button\" >Click here to back</a></center></p>");
            output.println("</body>" + "</html>\n");

        }else{
            userID = (Integer) session.getAttribute("userID");
            userPeanut = (Integer) session.getAttribute("amountPeanut");

            String name = request.getParameter("email");
            String info = "";
            UserDao ud = new UserDao();
            PrintWriter out = response.getWriter();
            info="Welcome " + name;

            //in to the problem solving spend 5 peanuts
            PaymentSystem.doPayment(userID,"",String.valueOf(userPeanut),"", AppNames.ProblemAndSolving,session);
            //ud.increaseUserPeanut(ud.queryUserPeanut(name),-5);
            //ud.increaseUserPeanut(ud.queryUserPeanut("jerry"),2);
            //ud.increaseUserPeanut(ud.queryUserPeanut("stefan"),3);
            //ud.updateTransaction(name,"app provider and platform",5,"using ProblemSolver app");

            request.setAttribute("welcome",info);
            request.getRequestDispatcher("problemSolving.jsp").forward(request, response);
        }
    }
}
