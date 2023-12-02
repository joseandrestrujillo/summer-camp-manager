<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Log in</title>
</head>
<body>
	<%
	String nextPage = "../controller/loginController.jsp";
	String messageNextPage = request.getParameter("message");
	
	%>
	
	<%= messageNextPage %>
	<br/>
	
	<form method="post" action="../controller/loginController.jsp">
		<label for="email">Email: </label>
		<input type="text" name="email">	
		<br/>
		<label for="password">Password: </label>
		<input type="password" name="password">
		<br/>
		<input type="submit" value="Entrar">
	</form>
	<a href="../controller/registerController.jsp">Registrarse</a>
	
</body>
</html>