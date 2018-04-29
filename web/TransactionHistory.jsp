<%--
  Created by IntelliJ IDEA.
  User: pegas
  Date: 4/29/2018
  Time: 3:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="CSS\eventList.css">
    <link rel="stylesheet" type="text/css" href="CSS\EventListSearch.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<% if (session.getAttribute("email") == null) { %>

<% } else {%>
<div id="myDiv" class="w3-top">
    <div class="w3-bar w3-white w3-padding w3-card myDiv2" style="letter-spacing:4px;">
        <a id="homePage"  href="#home" class="w3-bar-item w3-button">Welcome ${email}</a>
        <!-- Right-sided navbar links. Hide them on small screens -->
        <div class="w3-right w3-hide-small">
            <a href="TransactionHistory.jsp" class="w3-bar-item w3-button">Transaction History</a>
            <a href="#" class="w3-bar-item w3-button">Peanut: ${amountPeanut}</a>
            <a href="LogoutServlet" class="w3-bar-item w3-button">Log out</a>
        </div>
    </div>
</div>

<% } %>

<body>

<br><br><br>
<form class="example" action="">
    <input type="text" placeholder="Search Transaction.." id="search">
    <!--<button type="submit"><i class="fa fa-search"></i></button>-->
</form>

<section>
    <!--for demo wrap-->
    <h1>Transaction History</h1>
    <div class="tbl-header">
        <table cellpadding="0" cellspacing="0" border="0">
            <thead>
            <tr>
                <th>Transaction Amount</th>
                <th>Transaction Type</th>
                <th>Transaction Date/Time</th>
                <th>Application Name</th>
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
                $.each($("#table tbody").find("tr"), function() {
                    if($(this).find('td').text().toLowerCase().indexOf($(_this).val().toLowerCase()) == -1)
                        $(this).hide();
                    else
                        $(this).show();
                });
            });
        </script>
        <table cellpadding="0" cellspacing="0" border="0" id="table">
            <tbody>

            <%
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:3306/STUDENTS?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                    Connection conn = DriverManager.getConnection(url, "root", "");

                    Statement st = conn.createStatement();

                    String query = "select * from peanuttransaction where userid='" +session.getAttribute("userID")+"';";
                    System.out.println(query);
                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
            %>
            <tr>
                <td><%=rs.getString("transaction_amount")%></td>
                <td><% if(rs.getBoolean("payment")){%>(-)PAYMENT
                <%}else{%>(+)EARNING<%}%></td>
                <td><%=rs.getString("date_time")%></td>
                <td><%=rs.getString("appname")%></td>
                </td>
            </tr>

            <%}
                conn.close();
            }catch (Exception e){
            }
            %>
            </tbody>
        </table>
    </div>

    <%

        // for checking the session is available or not, If session dead go to Home page
        if (session == null) {
            session.invalidate();
        }
    %>
</section>
</body>
</html>
