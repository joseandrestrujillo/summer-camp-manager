<%@page import="business.utilities.Utils"%>
<%@page import="business.dtos.AssistantDTO"%>
<%@page import="display.web.javabean.AssistantInfoBean"%>
<%@page import="business.enums.UserRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="display.web.Container" %>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<jsp:useBean  id="assistantBean" scope="session" class="display.web.javabean.AssistantInfoBean"></jsp:useBean>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Prueba de MVC</title>
	</head>
	<body>
		<%
			if (customerBean.getRoleUser().equals(UserRole.ASSISTANT.name())) {
				if(!Container.getInstance().getUserManager().hasAssistantInfo(customerBean.getEmailUser())) {
					response.sendRedirect("./mvc/controller/registerAssistantController.jsp");
				} else {
					AssistantDTO assistant = Container.getInstance().getUserManager().getAssistantInfo(customerBean.getEmailUser());
					assistantBean.setDni(assistant.getId());
					assistantBean.setFirstName(assistant.getFirstName());
					assistantBean.setLastName(assistant.getLastName());
					assistantBean.setBirthDate(Utils.getStringDate(assistant.getBirthDate()));
					assistantBean.setRequireSpecialAttention(assistant.isRequireSpecialAttention());
				}
			} else {
				assistantBean.setDni(-1);
			}
		%>
		¡¡Bienvenido <jsp:getProperty property="emailUser" name="customerBean"/>!! 
		<a href="./mvc/controller/modifyUserController.jsp">Modificar datos de usuario</a>
		<a href="./mvc/controller/logoutController.jsp">Desconectar</a>
	</body>
</html>