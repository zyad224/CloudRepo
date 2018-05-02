<%@ page import="java.sql.*" %>
<link rel="stylesheet" type="text/css" href="CSS\eventList.css">
<link rel="stylesheet" type="text/css" href="CSS\EventListSearch.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<% if (session.getAttribute("email") == null) { %>
<div id="myDiv" class="w3-top">
	<div class="w3-bar w3-white w3-padding w3-card myDiv2" style="letter-spacing:4px;">
		<!-- Right-sided navbar links. Hide them on small screens -->
		<a href="index.jsp" class="w3-bar-item w3-button">Login</a>
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
			<a href="login.jsp" class="w3-bar-item w3-button">Applications</a>
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
	<input type="text" placeholder="Search Study Group.." id="search">
	<!--<button type="submit"><i class="fa fa-search"></i></button>-->
</form>

<section>
	<!--for demo wrap-->
	<h1>Study List</h1>
	<div class="tbl-header">
		<table cellpadding="0" cellspacing="0" border="0">
			<thead>
			<tr>
				<th>Study Title</th>
				<th>Study Place</th>
				<th>Study Date</th>
				<th>Study Time</th>
				<th>People To Attend</th>
				<th>Study Price</th>
				<th>Booking</th>
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
					String query = "select * from events";
					ResultSet rs = st.executeQuery(query);

					while (rs.next()) {
			%>
			<tr>
				<td><%=rs.getString("eventName")%></td>
				<td><%=rs.getString("place")%></td>
				<td><%=rs.getString("date")%></td>
				<td><%=rs.getString("time")%></td>
				<td><%=rs.getString("peopleToAttend")%></td>
				<td><%=rs.getString("price")%></td>
				<td><a href="BookEventServlet?Id=<%=rs.getInt("id") %>" class="button2">Book Me!</a></td>
				</td>
			</tr>

			<%}
			conn.close();
				}catch (Exception e){
				}
			%>

			<%--<%--%>
			<%--}--%>
			<%--%>--%>
			</tbody>
		</table>
	</div>

	<% if (session.getAttribute("email") == null) { %>

	<% } else {%>
			<a href="CreateEvent.jsp" class="button" style="vertical-align:middle"><span>Create Study</span></a>
			<a href="ShowMyEvents.jsp" class="button" style="vertical-align:middle"><span>My Studies</span></a>
			<a href="ShowMyBookings.jsp" class="button" style="vertical-align:middle"><span>My Bookings</span></a>
	<% } %>

	<%

		// for checking the session is available or not, If session dead go to Home page
		if (session == null) {
			session.invalidate();
		}
	%>
</section>

</body>