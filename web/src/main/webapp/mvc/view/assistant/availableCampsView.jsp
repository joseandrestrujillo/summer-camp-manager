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
	</head>
	<body>
		<jsp:include page="../common/components/header.jsp"></jsp:include>
		<main>
			<aside>
				<ul>
					<li><a href="/web/inscriptions">Inscripciones realizadas</a></li>
					<li><a href="/web/availableCamps">Campamentos disponibles</a></li>
				</ul>
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