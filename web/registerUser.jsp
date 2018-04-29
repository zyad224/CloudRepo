<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="CSS\util.css">
    <link rel="stylesheet" type="text/css" href="CSS\main.css">
</head>
<body>
<div class="limiter">
    <div class="container-login100">
        <div class="wrap-login100">
            <form class="login100-form validate-form p-l-55 p-r-55 p-t-178"
                  action="/RegisterUserServlet" method="post">
                <span class="login100-form-title">Sign Up</span>

                <div class="wrap-input100 validate-input m-b-16"
                     data-validate="Please enter your student E-Mail">
                    <input class="input100" type="text" name="uname" placeholder="Username"> <span class="focus-input100"></span>
                </div>

                <div class="wrap-input100 validate-input m-b-16" data-validate="Please enter password">
                    <input class="input100" type="password" name="password" placeholder="Password"> <span class="focus-input100"></span>
                </div>

                <div class="wrap-input100 validate-input m-b-16" data-validate="Again enter password">
                    <input class="input100" type="password" name="password2" placeholder="Confirm Password"> <span class="focus-input100"></span>
                </div>

                <div class="wrap-input100 validate-input m-b-16" data-validate="Please enter your first name ">
                    <input class="input100" type="text" name="fname" placeholder="First Name"> <span class="focus-input100"></span>
                </div>

                <div class="wrap-input100 validate-input m-b-16" data-validate="Please enter your last name ">
                    <input class="input100" type="text" name="lname" placeholder="Last Name"> <span class="focus-input100"></span>
                </div>
                <center>
                <select name="UserType">
                    <option value="">Please select your type</option>
                    <option value="regular">Regular User</option>
                    <option value="application">Application Provide</option>
                    <option value="microservice">Micro Service Provide</option>
                </select><br><br>


                <input type="radio" name="gender" value="male"> Male
                <input type="radio" name="gender" value="female"> Female
                <input type="radio" name="gender" value="other"> Other</center><br>

                <div class="container-login100-form-btn m-b-16">
                    <button class="login100-form-btn m-b-16" type="submit">Register</button>
                    <a href="index.jsp" class="login100-form-btn m-b-16">Cancel</a>
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>
