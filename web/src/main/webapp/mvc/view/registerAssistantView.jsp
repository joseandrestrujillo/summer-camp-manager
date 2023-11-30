<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	¡¡Bienvenido <jsp:getProperty property="emailUser" name="customerBean"/>!! 
	<a href="/web/mvc/controller/logoutController.jsp">Cerrar sesion</a>
	
    <h1>Introduzca su información de asistente</h1>
	<form action="../controller/registerAssistantController.jsp" method="post">
		<jsp:include page="./components/assistantInfoForm.jsp">
				<jsp:param name="dniAttribute" value="required"/>
				<jsp:param name="birthDateAttribute" value="required"/>
	 	</jsp:include>
	 	<input type="submit" value="Guardar"/>
 	</form>
</body>
</html>