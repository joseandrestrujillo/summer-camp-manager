<%@page import="business.enums.UserRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>    
<jsp:useBean  id="assistantBean" scope="session" class="display.web.javabean.AssistantInfoBean"></jsp:useBean>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modificar datos de usuario</title>
</head>
<body>
	<jsp:include page="./components/header.jsp"></jsp:include>
	<main>
		<h3>Modificar datos de usuario</h3>
		<form action="../controller/modifyUserController.jsp" method="post">
			<jsp:include page="./components/updateUserForm.jsp">
				<jsp:param name="email" value="<%= customerBean.getEmailUser() %>"/>
				<jsp:param name="password" value="<%= customerBean.getPasswordUser() %>"/>
				<jsp:param name="role" value="<%= customerBean.getRoleUser() %>"/>
				<jsp:param name="dni" value="<%= assistantBean.getDni() %>"/>
				<jsp:param name="firstName" value="<%= assistantBean.getFirstName() %>"/>
				<jsp:param name="lastName" value="<%= assistantBean.getLastName() %>"/>
				<jsp:param name="birthDate" value="<%= assistantBean.getBirthDate() %>"/>
				<jsp:param name="requireSpecialAttention" value="<%= assistantBean.isRequireSpecialAttention() %>"/>
			</jsp:include>
			
		 	<input type="submit" value="Guardar"/>
		</form>
	</main>
</body>
</html>