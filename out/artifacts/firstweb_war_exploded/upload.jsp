<%--
  Created by IntelliJ IDEA.
  User: Zeyad
  Date: 4/29/2018
  Time: 2:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;
  charset=ISO-8859-1"/>
    <title>File Upload</title>
</head>
<body>
<h1>File Upload</h1>
<form method="post" action="UploadServlet"
      enctype="multipart/form-data">
    <p>Select file to upload:
        <input type="file" name="file" size="60"/></p>
    <p><input type="submit" value="Upload"/></p>
</form>

<h1>${message} </h1>
</body>
</html>

