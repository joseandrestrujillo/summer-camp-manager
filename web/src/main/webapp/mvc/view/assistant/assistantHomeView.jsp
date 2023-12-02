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
List<CampDTO> camps = Container.getInstance().getInscriptionManager().avaliableCamps(Utils.getCurrentDate());

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
			<jsp:include page="../common/components/listOfCamps.jsp"></jsp:include>
		</main>
	</body>
</html>