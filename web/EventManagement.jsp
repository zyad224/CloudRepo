<%@ page import="java.sql.*" %>
<link rel="stylesheet" type="text/css" href="CSS\eventList.css">

<section>
	<!--for demo wrap-->
	<h1>Event List</h1>
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
				<td><button type="button" onclick="alert('Hello world!')">Book Me !</button>
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
	<a href="/CreateEvent.html" class="button" style="vertical-align:middle"><span>Create Event</span></a>
	<a href="/ShowMyEvents.jsp" class="button" style="vertical-align:middle"><span>Show my Events</span></a>

</section>