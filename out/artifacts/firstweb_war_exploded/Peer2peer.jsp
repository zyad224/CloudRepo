<%--
  Created by IntelliJ IDEA.
  User: Zeyad
  Date: 4/26/2018
  Time: 12:25 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.sql.*" %>
<link rel="stylesheet" type="text/css" href="CSS\eventList.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

<% if (session.getAttribute("email") == null) { %>

<% } else {%>
<div id="myDiv" class="w3-top">
    <div class="w3-bar w3-white w3-padding w3-card myDiv2" style="letter-spacing:4px;">
        <a id="homePage"  href="#home" class="w3-bar-item w3-button">Welcome ${email}</a>
        <!-- Right-sided navbar links. Hide them on small screens -->
        <div class="w3-right w3-hide-small">
            <a href="#" class="w3-bar-item w3-button">Create Event</a>
            <a href="#" class="w3-bar-item w3-button">Peanut: </a>
            <a href="LogoutServlet" class="w3-bar-item w3-button">Log out</a>
        </div>
    </div>
</div>
<% } %>

<br><br>

<section>
    <!--for demo wrap-->
    <h1>Help Request List</h1>
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
                <th>Booking</th>
            </tr>
            </thead>
        </table>
    </div>

    <div class="tbl-content">
        <table cellpadding="0" cellspacing="0" border="0">
            <tbody>

            <%
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:3306/STUDENTS?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                    Connection conn = DriverManager.getConnection(url, "root", "123");

                    Statement st = conn.createStatement();
                    String query = "select * from help";
                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
            %>
            <tr>
                <td><%=rs.getString("helpName")%></td>
                <td><%=rs.getString("place")%></td>
                <td><%=rs.getString("date")%></td>
                <td><%=rs.getString("time")%></td>
                <td><%=rs.getString("topic")%></td>
                <td><%=rs.getString("description")%></td>
                <td><%=rs.getString("mobile")%></td>
                <td><%=rs.getString("price")%></td>
                <td><button class="button" type="button" onclick="alert('Hello world!')">Book Me !</button>
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
    <a href="/CreateHelp.jsp" class="button" style="vertical-align:middle"><span>Create Help Request</span></a>
    <a href="/ShowMyHelps.jsp" class="button" style="vertical-align:middle"><span>My Help Requests</span></a>

</section>