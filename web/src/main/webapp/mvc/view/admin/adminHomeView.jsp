<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="business.dtos.CampDTO"%>
<%@page import="business.utilities.Utils"%>
<%@page import="business.dtos.AssistantDTO"%>
<%@page import="business.dtos.CampDTO"%>
<%@page import="display.web.javabean.AssistantInfoBean"%>
<%@page import="business.enums.UserRole"%>
<%@ page import ="display.web.Container" %>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<jsp:useBean  id="listOfCampsBean" scope="session" class="display.web.javabean.ListOfCampsBean"></jsp:useBean>

<%
List<CampDTO> camps = Container.getInstance().getCampsManager().listOfCamps();
listOfCampsBean.setCamps(camps);
%>

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
				<table>
			    	<thead>
			      	<tr>
			        	<th>ID</th>
			        	<th>Inscripciones</th>
				        <th>Parciales</th>
				        <th>Completas</th>
				        <th>Capacidad</th>
			      	</tr>
				    </thead>
				    <tbody>
			    		<%
			    		
			    		for(CampDTO camp: camps) {
			    			int numberOfInscriptions = Container.getInstance().getInscriptionManager().getNumberOfInscriptions(camp);
			    			int numberOfPartialInscriptions =Container.getInstance().getInscriptionManager().getNumberOfPartialInscriptions(camp);
			    			int numberOfCompleteInscription = numberOfInscriptions - numberOfPartialInscriptions;
			    		%>
					      	<tr>
					        	<td><%= camp.getCampID() %></td>
						        <td><%= numberOfInscriptions %></td>
						        <td><%= numberOfPartialInscriptions %></td>
						        <td><%= numberOfCompleteInscription %></td>
						        <td><%= camp.getCapacity() %></td>
					      	</tr>
				      	<%
				      	}
				      	%>
			    	</tbody>
			  </table>
		
		</main>
	</body>
</html>