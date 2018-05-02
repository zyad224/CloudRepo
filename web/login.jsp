<!DOCTYPE html>
<html>
<head>
<style>
    *{
        box-sizing: border-box;
    }

    .column {
        float: left;
        width: 33.33%;
        padding: 5px;
    }

    /* Clearfix (clear floats) */
    .row::after {
        content: "";
        clear: both;
        display: table;
    }
</style>

<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>

<% if (session.getAttribute("email") == null) { %>
<div id="myDiv" class="w3-top">
    <div class="w3-bar w3-white w3-padding w3-card myDiv2" style="letter-spacing:4px;">
        <!-- Right-sided navbar links. Hide them on small screens -->
        <a href="index.jsp" class="w3-bar-item w3-button">Login</a>
        <a href="upload.jsp" class="w3-bar-item w3-button"><img src="Images\upload.png" width="25" height="25"/>  Upload</a>
        <div class="w3-right w3-hide-small">
            <a href="login.jsp" class="w3-bar-item w3-button">Applications</a>
        </div>
    </div>
</div>
<% } else {%>
<div id="myDiv" class="w3-top">
    <div class="w3-bar w3-white w3-padding w3-card myDiv2" style="letter-spacing:4px;">
        <a id="homePage" href="#home" class="w3-bar-item w3-button">Welcome ${email}</a>
        <!-- Right-sided navbar links. Hide them on small screens -->
        <div class="w3-right w3-hide-small">
            <a href="upload.jsp" class="w3-bar-item w3-button"><img src="Images\upload.png" width="25" height="25"/>  Upload</a>
            <a href="login.jsp" class="w3-bar-item w3-button">Applications</a>
            <a href="TransactionHistory.jsp" class="w3-bar-item w3-button">Transaction History</a>
            <a href="" class="w3-bar-item w3-button">Peanut: ${amountPeanut}</a>
            <a href="LogoutServlet" class="w3-bar-item w3-button">Log out</a>
        </div>
    </div>
</div>
<% } %>

<%

    // for checking the session is available or not, If session dead go to Home page
    if (session == null) {
        session.invalidate();
    }
%>
<body>
<br><br><br>

<div class="row">
    <div class="column">
        <a href="EventManagement.jsp">
            <img src="Images\study.jpg" height="300" style="width:100%"/></a>
        <h4 align="center">Event Study</h4>
    </div>
    <div class="column">
        <a href="Peer2peer.jsp">
            <img align="center" src="Images\peer2peer.jpg" width="200" height="300" style="width:100%"/></a>
        <h4 align="center">Peer To Peer Help</h4>
    </div>
    <div class="column">
        <a href="QuestionIndex">
            <img align="center" src="Images\questionSolver.png" width="300" height="300" style="width:100%"/></a>
        <h4 align="center">Problem And Solving<h6 align="center">Provided by Team 07</h6></h4>
    </div>
</div>

</body>
</html>

