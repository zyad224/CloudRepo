package Team07.com.servlet;

import Team07.com.user.Question;
import Team07.com.user.Reply;
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
import java.util.Date;
import java.util.List;

@WebServlet(name = "QuestionReply")
public class QuestionReply extends HttpServlet {
    QuestionReplyDao qro=new QuestionReplyDao();
    UserDao userdao=new UserDao();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int userID = (int)request.getSession().getAttribute("userID");
        String name = (String)request.getSession().getAttribute("email");

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String methodname = request.getParameter("methodname");
        //add the question into the question list
        if(methodname.equals("addQuestion")){
            String questionInfo = request.getParameter("questionInfo");
            Question question=new Question();
            question.setQuestionInfo(questionInfo);
            User user=(User) session.getAttribute("user");
            question.setQuestionUser(userID);
            question.setQuestionUsername(name);
            question.setQuestiontime(new Date().toString());
            qro.insertQuestion(question);
            //the peanuts number

            //userdao.updateUserPeanut(user, user.getUserPeanuts()-50);
            //userdao.updateTransaction(user.getUserName(),"app provider and platform",50,"submitting a problem");
            //System.out.println("the current user massage :"+user.toString());
            //User suser=userdao.queryUserPeanut("stefan");
            //userdao.increaseUserPeanut(suser, 30);
            //User jsuser=userdao.queryUserPeanut("jerry");
            //userdao.increaseUserPeanut(jsuser, 20);
            List<Question> questionlist=qro.queryAllQuestion();
            request.setAttribute("questionlist", questionlist);
            request.getRequestDispatcher("problemSolving.jsp").forward(request, response);

        }else if(methodname.equals("addReply")){//follow the questionid
            String replyInfo = request.getParameter("replyInfo");
            String questionid = request.getParameter("questionid");
            Reply reply=new Reply();
            reply.setReplyInfo(replyInfo);
            User user=(User) session.getAttribute("user");
            reply.setReplyUser(userID);
            reply.setReplyQuestion(Integer.valueOf(questionid));
            reply.setReplyTime(new Date().toString());
            reply.setReplyusername(name);
            qro.insertReply(reply);
            List<Reply> replylist=qro.queryReplyByQuestionId(questionid);
            Question question=qro.queryQuestionByQuestionId(questionid);
            request.setAttribute("question", question);
            request.setAttribute("replylist", replylist);
            request.getRequestDispatcher("problemReply.jsp").forward(request, response);
        }else if(methodname.equals("addBestReply")){//select the best bestreply and count the peanuts
            String questionid = request.getParameter("questionid");
            List<Reply> replylist=qro.queryReplyByQuestionId(questionid);
            Question question=qro.queryQuestionByQuestionId(questionid);
            request.setAttribute("question", question);
            request.setAttribute("replylist", replylist);
            String bestreply=request.getParameter("bestanswer");
            question.setQuestionBestReply(Integer.valueOf(bestreply));
            qro.updateBestReply(question.getQuestionId(), Integer.valueOf(bestreply));
            Reply reply=qro.queryReplyByReplyId(bestreply);
            User user=userdao.queryUserPeanut(reply.getReplyusername());
            //get the best replay and give the peanuts to the customer
            qro.updateUserPeanut(user.getUserId(), user.getUserPeanuts()+30);
            userdao.updateTransaction(((User)(request.getSession().getAttribute("user"))).getUserName(),"replayer",30,"Best replay");
            request.getRequestDispatcher("problemReply.jsp").forward(request, response);

        }

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String methodname = request.getParameter("methodname");
        if(methodname.equals("queryQuestion")){//
            List<Question> questionlist=qro.queryAllQuestion();
            request.setAttribute("questionlist", questionlist);
            request.getRequestDispatcher("problemSolving.jsp").forward(request, response);

        }else if(methodname.equals("queryReply")){
            String questionid = request.getParameter("questionid");
            Question question=qro.queryQuestionByQuestionId(questionid);
            List<Reply> replylist=qro.queryReplyByQuestionId(questionid);
            request.setAttribute("replylist", replylist);
            request.setAttribute("question", question);
            request.getRequestDispatcher("problemReply.jsp").forward(request, response);
        }

    }
}
