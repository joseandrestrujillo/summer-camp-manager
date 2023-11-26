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
if (messageNextPage == null) messageNextPage = "";

if (customerBean != null && !customerBean.getEmailUser().equals("")) {
	nextPage = "../../index.jsp";
} else {
%>
<%= messageNextPage %><br/><br/>
<form method="post" action="../controller/loginController.jsp">
	<label for="email">Email: </label>
	<input type="text" name="email" value="john.doe@email.com">	
	<br/>
	<label for="password">Password: </label>
	<input type="password" name="password">
	<br/>
	<input type="submit" value="Submit">
</form>
<%
}
%>

</body>
</html>