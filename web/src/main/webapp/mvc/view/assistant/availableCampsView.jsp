<%@page import="business.utilities.Utils"%>
<%@page import="display.web.Container"%>
<%@page import="business.dtos.CampDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="listOfCampsBean" scope="session" class="display.web.javabean.ListOfCampsBean"></jsp:useBean>

<%
List<CampDTO> camps = Container.getInstance().getInscriptionManager().avaliableCamps(Utils.getCurrentDate());
listOfCampsBean.setCamps(camps);
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Campamentos disponibles</title>
		<script defer src="${pageContext.request.contextPath}/scripts/filterAvailableCamps.js"></script>
	</head>
	<body>
		<jsp:include page="../common/components/header.jsp"></jsp:include>
		<main>
			<aside>
				<ul>
					<li><a href="/web/inscriptions">Inscripciones realizadas</a></li>
					<li><a href="/web/availableCamps">Campamentos disponibles</a></li>
				</ul>
				
				<form>
					<label for="since">Desde:</label>
					<input type="date" name="since" id="since" required>
					<br>
					
					<label for="until">Hasta:</label>
					<input type="date" name="until" id="until" required>
					<br>
					
					<label for="educative_level">Nivel Educativo:</label>
					<select name="educative_level" id="educative_level">
					  <option value="ELEMENTARY">Infantil</option>
					  <option value="PRESCHOOL">Juvenil</option>
					  <option value="TEENAGER">Adolescente</option>
					</select>
					<br>
					
					<label for="capacity">Capacidad:</label>
					<input type="number" name="capacity" id="capacity" min="0" max="999" required>
					<br>
				</form>
			</aside>
			
			<%
				String enrollMsg = (String) request.getSession().getAttribute("enrollMsg");
				String enrollError = (String) request.getSession().getAttribute("enrollError");
			
				if (enrollError != null) {
			%>
				<p style="color: red;"><%= enrollError %></p>
			<%
				} else if (enrollMsg != null) {
			%>
				<p style="color: green;"><%= enrollMsg %></p>
			<%
				}
			%>
			
			<form action="/web/enroll" method="post">
				<label for="type">Tipo de inscripción:</label>
				<select name="type" id="type">
				  <option value="COMPLETE" selected>Completa</option>
				  <option value="PARTIAL">Parcial</option>
				</select>
				<br>
				<input type="submit" value="Inscribirse"><br>
				<jsp:include page="../common/components/listOfAvailableCamps.jsp"></jsp:include>
			</form>
		</main>
	</body>
</html>