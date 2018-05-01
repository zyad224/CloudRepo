<!DOCTYPE html>
<html lang="en">
<head>
    <title>Create Event</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="CSS\createEvent.css">
    <link rel="stylesheet" type="text/css" href="CSS\createEventUtil.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>

<% if (session.getAttribute("email") == null) { %>
<div id="myDiv" class="w3-top">
    <div class="w3-bar w3-white w3-padding w3-card myDiv2" style="letter-spacing:4px;">
        <a href="index.jsp" class="w3-bar-item w3-button">Login</a>
        <!-- Right-sided navbar links. Hide them on small screens -->
        <div class="w3-right w3-hide-small">
            <a href="login.jsp" class="w3-bar-item w3-button">Applications</a>
            <a href="EventManagement.jsp" class="w3-bar-item w3-button">Event Studies</a>
            <a href="Peer2peer.jsp" class="w3-bar-item w3-button">Peer to Peer</a>
        </div>
    </div>
</div>
<% } else {%>
<div id="myDiv" class="w3-top">
    <div class="w3-bar w3-white w3-padding w3-card myDiv2" style="letter-spacing:4px;">
        <a id="homePage"  href="#home" class="w3-bar-item w3-button">Welcome ${email}</a>
        <!-- Right-sided navbar links. Hide them on small screens -->
        <div class="w3-right w3-hide-small">
            <a href="login.jsp" class="w3-bar-item w3-button">Applications</a>
            <a href="EventManagement.jsp" class="w3-bar-item w3-button">Event Studies</a>
            <a href="Peer2peer.jsp" class="w3-bar-item w3-button">Peer to Peer</a>
            <a href="TransactionHistory.jsp" class="w3-bar-item w3-button">Transaction History</a>
            <a href="#" class="w3-bar-item w3-button">Peanut: ${amountPeanut}</a>
            <a href="LogoutServlet" class="w3-bar-item w3-button">Log out</a>
        </div>
    </div>
</div>
<% } %>

<div class="container-contact100">
    <div class="wrap-contact100">
        <form class="contact100-form validate-form" action="CreateEventServlet" method="post">
            <span class="contact100-form-title">
               Create Your Study Group
            </span>

            <div class="wrap-input100 validate-input" data-validate="Event title is required">
                <span class="label-input100">Study Name</span>
                <input class="input100" type="text" name="Title" placeholder="Enter your study title">
                <span class="focus-input100"></span>
            </div>

            <div class="wrap-input100 validate-input" data-validate = "Event place is required">
                <span class="label-input100">Study Place</span>
                <input class="input100" type="text" name="Place" placeholder="Enter your study place">
                <span class="focus-input100"></span>
            </div>

            <div class="wrap-input100 validate-input" data-validate="Event date is required">
                <span class="label-input100">Study date</span>
                <input class="input100" type="text" name="Date" placeholder="Enter your study date">
                <span class="focus-input100"></span>
            </div>

            <div class="wrap-input100 validate-input" data-validate = "Event place is required">
                <span class="label-input100">Study Time</span>
                <input class="input100" type="text" name="Time" placeholder="Enter your study time">
                <span class="focus-input100"></span>
            </div>

            <div class="wrap-input100 validate-input" data-validate = "Price is required">
                <span class="label-input100">People to attend</span>
                <input class="input100" name="People to attend" placeholder="How many people can attend?"></input>
                <span class="focus-input100"></span>
            </div>

            <div class="wrap-input100 validate-input" data-validate = "Price is required">
                <span class="label-input100">Price</span>
                <input class="input100" name="Price" placeholder="What is your study price?"></input>
                <span class="focus-input100"></span>
            </div>



            <div class="container-contact100-form-btn">
                <div class="wrap-contact100-form-btn">
                    <div class="contact100-form-bgbtn"></div>
                    <button class="contact100-form-btn" type="submit">Submit
                        <span><i class="fa fa-long-arrow-right m-l-7" aria-hidden="true"></i></span>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<div id="dropDownSelect1"></div>
<%
    // for checking the session is available or not, If session dead go to Home page
    if (session == null) {
        session.invalidate();
    }
%>
</body>
</html>