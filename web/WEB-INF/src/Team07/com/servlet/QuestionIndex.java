package Team07.com.servlet;

import Team07.com.user.User;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("user");
        String info="";

        UserDao ud=new UserDao();

        PrintWriter out=response.getWriter();
        if (user==null){
            out.print("<script language='javascript'>alert('You haven't log in,please log in first');window.location.href='login.jsp';</script>");
        }else{
            info="Welcome "+user.getUserName();

            //in to the problem solving spend 5 peanuts
            ud.increaseUserPeanut(ud.queryUserPeanut(user.getUserName()),-5);
            ud.increaseUserPeanut(ud.queryUserPeanut("jerry"),2);
            ud.increaseUserPeanut(ud.queryUserPeanut("stefan"),3);
            ud.updateTransaction(user.getUserName(),"app provider and platform",5,"using ProblemSolver app");
            request.setAttribute("welcome",info);
            request.getRequestDispatcher("/problemSolving.jsp").forward(request, response);
        }

    }
}
