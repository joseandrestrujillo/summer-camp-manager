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
<%
String nextPage = "../controller/registerController.jsp";
String messageNextPage = request.getParameter("message");
if (messageNextPage == null) messageNextPage = "";

if (customerBean != null && !customerBean.getEmailUser().equals("")) {
	nextPage = "../../index.jsp";
} else {
%>
<%= messageNextPage %>
<br/><br/>
<form method="post" action="../controller/registerController.jsp">
  <fieldset>
    <legend>Registro</legend>
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
  </fieldset>
</form>
<%
}
%>

</body>
</html>