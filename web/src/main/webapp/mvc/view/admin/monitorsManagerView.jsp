<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Summer Camp</title>
	</head>
	<body>
		<jsp:include page="../common/components/header.jsp"></jsp:include>
		<main>
			<aside>
			  <ul>
			    <li><a href="/web/campsManager">Campamentos</a></li>
			    <li><a href="/web/activitiesManager">Actividades</a></li>
			    <li><a href="/web/monitorsManager">Monitores</a></li>
			    <li><a href="/web/assistantsManager">Asistentes</a></li>
			  </ul>
			</aside>
			
			<a href="/web/createMonitor">Dar de alta un nuevo monitor</a>
			<%
				String createMonitorMsg = (String) request.getSession().getAttribute("createMonitorMsg");
				String createMonitorError = (String) request.getSession().getAttribute("createMonitorError");
			
				if (createMonitorError != null) {
			%>
				<p style="color: red;"><%= createMonitorError %></p>
			<%
				} else if (createMonitorMsg != null) {
			%>
				<p style="color: green;"><%= createMonitorMsg %></p>
			<%
				}
			%>
			<jsp:include page="../common/components/listOfMonitors.jsp"></jsp:include>
		</main>

</body>
</html>