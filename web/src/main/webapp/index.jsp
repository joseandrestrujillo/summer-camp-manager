<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Prueba de MVC</title>
	</head>
	<body>
		
		<% 
		if (request.getParameter("reset") != null) {
		%>
		<jsp:setProperty property="emailUser" value="" name="customerBean"/>
		<%
		}
		if (customerBean == null || customerBean.getEmailUser()=="") {
		%>
		<a href="./mvc/controller/loginController.jsp">Acceder</a>
		<a href="./mvc/controller/registerController.jsp">Registrarse</a>
		<% } else { %>
			¡¡Bienvenido <jsp:getProperty property="emailUser" name="customerBean"/>!! 
			<a href="./mvc/controller/logoutController.jsp">Cerrar sesion</a>
		<% } %>
	</body>
</html>