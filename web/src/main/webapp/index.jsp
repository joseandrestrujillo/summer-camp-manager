<%@page import="business.enums.UserRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="display.web.Container" %>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Prueba de MVC</title>
	</head>
	<body>
		<%
		if (customerBean == null || customerBean.getEmailUser()=="") {
		%>
			<a href="./mvc/controller/loginController.jsp">Acceder</a>
			<a href="./mvc/controller/registerController.jsp">Registrarse</a>
		<% 
		} else { 
			if (customerBean.getRoleUser().equals(UserRole.ASSISTANT.name())) {
				if(!Container.getInstance().getUserManager().hasAssistantInfo(customerBean.getEmailUser())) {
					response.sendRedirect("./mvc/controller/registerAssistantController.jsp");
				}
			}
		%>
			¡¡Bienvenido <jsp:getProperty property="emailUser" name="customerBean"/>!! 
			<a href="./mvc/controller/modifyUserController.jsp">Modificar datos de usuario</a>
			<a href="./mvc/controller/logoutController.jsp">Desconectar</a>
		<% 
		} 
		%>
	</body>
</html>