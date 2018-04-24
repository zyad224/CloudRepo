<%@ page 	import="javax.xml.parsers.DocumentBuilderFactory,
javax.xml.parsers.DocumentBuilder,org.w3c.dom.*"%>
<link rel="stylesheet" type="text/css" href="CSS\eventList.css">
<%
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder db = dbf.newDocumentBuilder();
	Document doc = db.parse("C:\\Users\\pegas\\IdeaProjects\\firstweb\\web\\Events.xml");
	NodeList nList = doc.getElementsByTagName("event");
	int childNode = nList.getLength();

	NodeList nl = doc.getElementsByTagName("title");
	NodeList n2 = doc.getElementsByTagName("place");
	NodeList n3 = doc.getElementsByTagName("date");
	NodeList n4 = doc.getElementsByTagName("price");
%>
<section>

	<!--for demo wrap-->
	<h1>Event List</h1>
	<div class="tbl-header">
		<table cellpadding="0" cellspacing="0" border="0">
			<thead>
			<tr>
				<th>Event ID</th>
				<th>Event Title</th>
				<th>Event Place</th>
				<th>Event Date</th>
				<th>Event Price</th>
			</tr>
			</thead>
		</table>
	</div>

	<div class="tbl-content">
		<table cellpadding="0" cellspacing="0" border="0">
			<tbody>
			<tr>
				<%
					for (int i = 0; i < childNode; i++) {
				%>
				<td><%=nl.item(i).getFirstChild().hashCode()%></td>
				<td><%=nl.item(i).getFirstChild().getNodeValue()%></td>
				<td><%=n2.item(i).getFirstChild().getNodeValue()%></td>
				<td><%=n3.item(i).getFirstChild().getNodeValue()%></td>
				<td><%=n4.item(i).getFirstChild().getNodeValue()%></td>
			</tr>
			<%
				}
			%>
			</tbody>
		</table>
	</div>
	<a href="/CreateEvent.html" class="button" style="vertical-align:middle"><span>Create Event</span></a>
</section>