<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Camping Site</title>
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
			
			<a href="/web/createActivity">Crear una nueva actividad</a>
			<%
				String createActivityMsg = (String) request.getSession().getAttribute("createActivityMsg");
				String createActivityError = (String) request.getSession().getAttribute("createActivityError");
			
				if (createActivityError != null) {
			%>
				<p style="color: red;"><%= createActivityError %></p>
			<%
				} else if (createActivityMsg != null) {
			%>
				<p style="color: green;"><%= createActivityMsg %></p>
			<%
				}
			%>
			<jsp:include page="../common/components/listOfActivities.jsp"></jsp:include>
			
		</main>

</body>
</html>