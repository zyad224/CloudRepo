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
            <a href="#" class="w3-bar-item w3-button">Peanut: </a>
            <a href="LogoutServlet" class="w3-bar-item w3-button">Log out</a>
        </div>
    </div>
</div>
<% } %>

<br><br><br>
<body>
<form class="example" action="">
    <input type="text" placeholder="Search Study Group.." id="search">
    <!--<button type="submit"><i class="fa fa-search"></i></button>-->
</form>

<section>
    <!--for demo wrap-->
    <h1>${email} Study List</h1>
    <div class="tbl-header">
        <table cellpadding="0" cellspacing="0" border="0">
            <thead>
            <tr>
                <th>Study Title</th>
                <th>Study Place</th>
                <th>Study Date</th>
                <th>Study Time</th>
                <th>People to Attend</th>
                <th>Study Price</th>
                <th>User</th>
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
                $.each($("#ShowMyEvents tbody").find("tr"), function() {
                   if($(this).find('td').text().toLowerCase().indexOf($(_this).val().toLowerCase()) == -1)
                        $(this).hide();
                    else
                        $(this).show();
                });
            });
        </script>
        <table cellpadding="0" cellspacing="0" border="0" id="ShowMyEvents">
            <tbody>

            <%
                try {

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:3306/students";

                    Connection conn = DriverManager.getConnection(url, "root", "123");

                    String s = (String)session.getAttribute("email");
                    String p=(String) session.getAttribute("password");
                    System.out.println(s+""+p);

                    Statement st = conn.createStatement();

                    String query2="select id from users where username='"+s + "' AND password='" + p +"';";
                    ResultSet rs = st.executeQuery(query2);
                    rs.next();
                    int id= rs.getInt("id");
                    String query3= "select * from events where userID='" + id +"';";
                    ResultSet rs2=st.executeQuery(query3);

                    while (rs2.next()) {
                        System.out.println(rs2.getString("eventName"));


            %>
            <tr>
                <td><%=rs2.getString("eventName")%></td>
                <td><%=rs2.getString("place")%></td>
                <td><%=rs2.getString("date")%></td>
                <td><%=rs2.getString("time")%></td>
                <td><%=rs2.getString("peopleToAttend")%></td>
                <td><%=rs2.getString("price")%></td>
                <td><%=s%></td>
                <td><a href="DeleteEvent?Id=<%=rs2.getInt("id") %>" class="button2">Delete</a></td>
                </td>
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