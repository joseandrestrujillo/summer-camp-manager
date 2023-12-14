<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/common.css">
</head>
<body>
	<jsp:include page="../common/components/header.jsp"></jsp:include>
	<main>
		<h3>Introduzca su información de asistente</h3>
		<form action="../controller/registerAssistantController.jsp" method="post">
			<jsp:include page="../common/components/assistantInfoForm.jsp">
					<jsp:param name="dniAttribute" value="required"/>
					<jsp:param name="birthDateAttribute" value="required"/>
		 	</jsp:include>
		 	<input type="submit" value="Guardar"/>
	 	</form>
	</main>
</body>
</html>