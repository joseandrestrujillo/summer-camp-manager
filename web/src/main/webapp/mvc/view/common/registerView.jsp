<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign up</title>
</head>
<body>
	<jsp:include page="./components/message.jsp"></jsp:include>
	<form method="post" action="../controller/registerController.jsp">
	    <label for="email">Email: </label>
	    <input type="email" name="email" required>
	    <br/>
	    <label for="password">Password: </label>
	    <input type="password" name="password" required>
	    <br/>
	    <label for="role">Rol: </label>
	    <select name="role" required>
	      <option value="ADMIN">Administrador</option>
	      <option value="ASSISTANT" selected>Asistente</option>
	    </select>
	    <br/>
	    <input type="submit" value="Registrar">
	</form>
	
	<a href="../../controller/loginController.jsp">Acceder</a>
	
</body>
</html>