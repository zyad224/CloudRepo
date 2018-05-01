<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Error</title>
	<style>
		.container {
			position: relative;
			width: 100%;
			height: auto;
		}

		.container img {
			width: 100%;
			height: auto;
		}

		.container .btn {
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
			-ms-transform: translate(-50%, -50%);
			background-color: #555;
			color: white;
			font-size: 16px;
			padding: 12px 24px;
			border: none;
			cursor: pointer;
			border-radius: 5px;
			text-align: center;
		}

		.container .btn:hover {
			background-color: black;
		}
	</style>
</head>
<body>
<div class="container">
	<img src="Images/errorPage.jpg" alt="Error" style="width:100%">
	<a href="index.jsp" class="btn">Back To Home Page</a>
</div>
</body>
</html>
