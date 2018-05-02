<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <title>Upload Form</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <!-- Google web fonts -->
    <link href="http://fonts.googleapis.com/css?family=PT+Sans+Narrow:400,700" rel='stylesheet' />

    <!-- The main CSS file -->
    <link href="CSS/uploadStyle.css" rel="stylesheet" />
</head>

<% if (session.getAttribute("email") == null) { %>
<div id="myDiv" class="w3-top">
    <div class="w3-bar w3-white w3-padding w3-card myDiv2" style="letter-spacing:4px;">
        <a href="index.jsp" class="w3-bar-item w3-button">Login</a>
        <!-- Right-sided navbar links. Hide them on small screens -->
        <div class="w3-right w3-hide-small">
            <a href="login.jsp" class="w3-bar-item w3-button">Applications</a>
        </div>
    </div>
</div>
<% } else {%>
<div id="myDiv" class="w3-top">
    <div class="w3-bar w3-white w3-padding w3-card myDiv2" style="letter-spacing:4px;">
        <a id="homePage"  href="#home" class="w3-bar-item w3-button">Welcome ${email}</a>
        <!-- Right-sided navbar links. Hide them on small screens -->
        <div class="w3-right w3-hide-small">
            <a href="upload.jsp" class="w3-bar-item w3-button"><img src="Images\upload.png" width="25" height="25"/>  Upload</a>
            <a href="login.jsp" class="w3-bar-item w3-button">Applications</a>
            <a href="TransactionHistory.jsp" class="w3-bar-item w3-button">Transaction History</a>
            <a href="#" class="w3-bar-item w3-button">Peanut: ${amountPeanut}</a>
            <a href="LogoutServlet" class="w3-bar-item w3-button">Log out</a>
        </div>
    </div>
</div>
<% } %>


<body>

<form id="upload" method="post" action="UploadServlet" enctype="multipart/form-data">
    <div id="drop">
        Drop Here

        <a>Browse</a>
        <input type="file" name="upl" multiple />
    </div>

    <ul>
        <!-- The file uploads will be shown here -->
    </ul>

</form>
<h1>${message} </h1>
<!-- JavaScript Includes -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="js/jquery.knob.js"></script>

<!-- jQuery File Upload Dependencies -->
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.iframe-transport.js"></script>
<script src="js/jquery.fileupload.js"></script>

<!-- Our main JS file -->
<script src="js/script.js"></script>

</body>
</html>

