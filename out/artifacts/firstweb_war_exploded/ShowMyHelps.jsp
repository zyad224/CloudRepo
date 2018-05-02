<%--
  Created by IntelliJ IDEA.
  User: Zeyad
  Date: 4/26/2018
  Time: 12:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.sql.*" %>
<link rel="stylesheet" type="text/css" href="CSS\eventList.css">
<link rel="stylesheet" type="text/css" href="CSS\EventListSearch.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

<% if (session.getAttribute("email") == null) { %>
<div id="myDiv" class="w3-top">
    <div class="w3-bar w3-white w3-padding w3-card myDiv2" style="letter-spacing:4px;">
        <a href="index.jsp" class="w3-bar-item w3-button">Login</a>
        <a href="upload.jsp" class="w3-bar-item w3-button"><img src="Images\upload.png" width="25" height="25"/>  Upload</a>
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

<br><br><br>

<body>
<form class="example" action="">
    <input type="text" placeholder="Search Helps.." id="search">
    <!--<button type="submit"><i class="fa fa-search"></i></button>-->
</form>

<section>
    <!--for demo wrap-->
    <h1>${email} Help Requests</h1>
    <div class="tbl-header">
        <table cellpadding="0" cellspacing="0" border="0">
            <thead>
            <tr>
                <th>Help Title</th>
                <th>Help Place</th>
                <th>Help Date</th>
                <th>Help Time</th>
                <th>Help Topic</th>
                <th>Description</th>
                <th>Mobile</th>
                <th>Price</th>
                <th>User</th>
                <th>People</th>
                <th>Delete</th>
            </tr>
            </thead>
        </table>
    </div>

    <div class="tbl-content">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script>
            // Write on keyup event of keyword input element
            $("#search").keyup(function(){
                _this = this;
                // Show only matching TR, hide rest of them
                $.each($("#ShowMyHelps tbody").find("tr"), function() {

                    if($(this).find('td').text().toLowerCase().indexOf($(_this).val().toLowerCase()) == -1)
                        $(this).hide();
                    else
                        $(this).show();
                });
            });
        </script>
        <table cellpadding="0" cellspacing="0" border="0" id="ShowMyHelps">
            <tbody>

            <%
                try {

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:3306/students";
                    Connection conn = DriverManager.getConnection(url, "root", "123");

                    String s = (String)session.getAttribute("email");
                    String p=(String) session.getAttribute("password");

                    Statement st = conn.createStatement();

                    String query2="select id from users where username='"+s + "' AND password='" + p +"';";
                    ResultSet rs = st.executeQuery(query2);
                    rs.next();
                    int id= rs.getInt("id");
                    System.out.println(rs.getInt("id"));
                    String query3= "select * from help where userID='" + id +"';";
                    ResultSet rs2=st.executeQuery(query3);

                    while (rs2.next()) {
                      //  System.out.println(rs2.getString("eventName"));

            %>
            <tr>
                <td><%=rs2.getString("helpName")%></td>
                <td><%=rs2.getString("place")%></td>
                <td><%=rs2.getString("date")%></td>
                <td><%=rs2.getString("time")%></td>
                <td><%=rs2.getString("topic")%></td>
                <td><%=rs2.getString("description")%></td>
                <td><%=rs2.getString("mobile")%></td>
                <td><%=rs2.getString("price")%></td>
                <td><%=s%></td>
                <td><%=rs2.getString("peopleToAttend")%></td>
                <% if (session.getAttribute("email") != null) { %>
                <td><a href="DeleteHelp?Id=<%=rs2.getInt("id") %>" class="button2">Delete</a></td>
                <%}%>
            </tr>

            <%}
            }catch (Exception e){
            }
            %>

            <%--<%--%>
            <%--}--%>
            <%--%>--%>
            </tbody>
        </table>
    </div>
</section>

<%

    // for checking the session is available or not, If session dead go to Home page
    if (session == null) {
        session.invalidate();
    }
%>

</body>