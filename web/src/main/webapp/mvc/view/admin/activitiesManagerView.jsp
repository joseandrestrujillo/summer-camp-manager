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
			
			<jsp:include page="../common/components/message.jsp"></jsp:include>

			<jsp:include page="../common/components/listOfActivities.jsp"></jsp:include>
			
		</main>

</body>
</html>