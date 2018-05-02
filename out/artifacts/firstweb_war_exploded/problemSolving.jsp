<%@ page import="Team07.com.user.User" %>
<%@ page import="Team07.com.userdao.UserDao" %>
<%@ page import="Team07.com.user.Question" %>
<%@ page import="java.util.List" %>
<%@ page import="Team07.com.userdao.QuestionReplyDao" %><%--
  Created by IntelliJ IDEA.
  User: Jerry
  Date: 2018/4/21
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <title>Problem and solving</title>
    <meta charset="utf-8"/>
    <meta  name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Tangerine">
    <style>
        a.ex1
        {
            margin-left:10cm;
        }
    </style>
    <style>
        .w3-tangerine {
            font-family: "Tangerine", serif;
        }
    </style>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    //judgemnet user log in
    if(user!=null){//log in
        String uname = user.getUserName();
        UserDao ud = new UserDao();
        //show the peanuts
        User user1 = ud.queryUserPeanut(uname);
        session.setAttribute("user",user1);

    }else{//no log in
        //response.sendRedirect("login.jsp");
        //return;
    }
%>
<div class="">
    <div class="w3-bar w3-light-grey">
        <a href="login.jsp" class="w3-bar-item w3-large w3-button ">Home</a>
        <a class="w3-bar-item w3-text w3-text-pink w3-large w3-tangerine">Welcome ${email}!</a>
        <a class="ex1 w3-bar-item w3-large w3-button"></a>
        <div class="w3-dropdown-hover">
            <button class="w3-button w3-large">Peanuts ${amountPeanut}
            </button>
        </div>
        <a class="ex2 w3-bar-item w3-button w3-large" href="MyQuestion">My Problems</a>
        <a class="ex2 w3-bar-item w3-button w3-large" href="login">Logout</a>
    </div>
    <div class="w3-container w3-text-black">
        <div class="w3-display-container w3-text-black w3-tangerine"align="center">
            <img src="Images/question.jpg" class="w3-circle" alt="Norway" align="middle"  style="width:100%;height:6.5cm">
            <div class="w3-display-middle w3-jumbo">Problem and Solving</div>
        </div>
        <div class="w3-large">Question Lists:</div>
    </div>
    <%
        QuestionReplyDao qro=new QuestionReplyDao();
        List<Question> questionlist=qro.queryAllQuestion();
        request.setAttribute("questionlist", questionlist);

    %>
    <div class="w3-container">
        <div class="w3-panel w3-pale-blue w3-leftbar w3-rightbar w3-border-blue">
            <div class="w3-text w3-large">QUESTION :</div>
            <c:forEach var="ql" items="${questionlist}" varStatus="status">
                <div class="w3-text w3-text-red w3-large">${status.count}.<a href="QuestionReply?methodname=queryReply&questionid=${ql.questionId}">
                    <c:if test="${fn:length(ql.questionInfo)<30}">
                        ${ql.questionInfo}
                    </c:if>
                    <c:if test="${fn:length(ql.questionInfo)>=30}">
                        ${fn:substring(ql.questionInfo,0 , 30)} .......
                    </c:if>

                </a></div>
                <div class="w3-text w3-text-brown">Post by ${ql.questionUsername} at ${ql.questiontime }.</div>
            </c:forEach>

        </div>

    </div>
    <form action="QuestionReply" method="post">
        <input type="hidden" name="methodname" value="addQuestion">
        <div class="w3-container">
            <div class="w3-large">Your Question</div>
            <div class="w3-left-align "><p>
                <textarea name="questionInfo" cols="100" rows="8" placeholder="please input your problem!"></textarea>
            </p></div>
        </div>

        <p>
            <input class="w3-button w3-black w3-round-large" type="submit" value="Submit"/>
    </form>
</div>

</body>
</html>
