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
			    <li><a href="">Monitores</a></li>
			    <li><a href="">Asistentes</a></li>
			  </ul>
			</aside>
			
			<a href="/web/createCamp">Crear nuevo campamento</a>
			<%
				String createCampMsg = (String) request.getSession().getAttribute("createCampMsg");
				String createCampError = (String) request.getSession().getAttribute("createCampError");
			
				if (createCampError != null) {
			%>
				<p style="color: red;"><%= createCampError %></p>
			<%
				} else if (createCampMsg != null) {
			%>
				<p style="color: green;"><%= createCampMsg %></p>
			<%
				}
			%>
			<jsp:include page="../common/components/listOfCamps.jsp"></jsp:include>
			
		</main>

</body>
</html>