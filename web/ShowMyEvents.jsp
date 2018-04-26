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
    <h1>${email} Event List</h1>
    <div class="tbl-header">
        <table cellpadding="0" cellspacing="0" border="0">
            <thead>
            <tr>
                <th>Event Title</th>
                <th>Event Place</th>
                <th>Event Date</th>
                <th>Event Time</th>
                <th>People</th>
                <th>Event Price</th>
                <th>User</th>
                <th>Delete</th>
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
                    String s = (String)session.getAttribute("email");
                    String p=(String) session.getAttribute("password");
                    System.out.println(s+""+p);

                    Statement st = conn.createStatement();

                    String query2="select id from users where username='"+s + "' AND password='" + p +"';";
                    ResultSet rs = st.executeQuery(query2);
                    rs.next();
                    int id= rs.getInt("id");
                    //System.out.println("in");
                    //System.out.println(rs.getInt("id"));
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
                <%--<%System.out.println("this is is:"+rs2.getInt("id"));%>--%>

                <td><a href="DeleteEvent?Id=<%=rs2.getInt("id") %>">delete</a></td>
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