package Team07.com.servlet;

import Team07.com.user.Question;
import Team07.com.user.User;
import Team07.com.userdao.QuestionReplyDao;
import Team07.com.userdao.UserDao;

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
        request.setCharacterEncoding("utf-8");

        HttpSession session=request.getSession();
        int userID = (int)session.getAttribute("userID");

        QuestionReplyDao qr = new QuestionReplyDao();
        ArrayList<Question> list =  (ArrayList<Question>) qr.queryAllMyQuestion(userID);
        request.setAttribute("myQuestionList",list);
        request.getRequestDispatcher("myProblems.jsp").forward(request, response);

    }
}
