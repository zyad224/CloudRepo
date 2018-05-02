<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login Page</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="CSS\util.css">
    <link rel="stylesheet" type="text/css" href="CSS\main.css">
</head>
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
<body>
<div class="limiter">
    <div class="container-login100">
        <div class="wrap-login100">
            <form class="login100-form validate-form p-l-55 p-r-55 p-t-178"
                  action="LogoutServlet" method="post">
                <span class="login100-form-title"> Member Logout </span>

                <div class="wrap-input100 validate-input m-b-16"
                     data-validate="Please enter username">
                    <input class="input100" type="text" name="email"
                           placeholder="Username"> <span class="focus-input100"></span>
                </div>

                <div class="wrap-input100 validate-input"
                     data-validate="Please enter password">
                    <input class="input100" type="password" name="password"
                           placeholder="Password"> <span class="focus-input100"></span>
                </div>
                <br><br>

                <div class="container-login100-form-btn">
                    <button class="login100-form-btn" type="submit">Sign Out</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>