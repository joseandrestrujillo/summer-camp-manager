<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dar de alta nuevo monitor</title>
</head>
<body>
	<jsp:include page="../../common/components/header.jsp"></jsp:include>
	<main>
		<form action="/web/createMonitor" method="post">
			<label for="dni">DNI</label>
			<input type="text" id="dni" name="dni" pattern="[0-9]{8}[A-Z]" required>
			<br>
			
			<label for="firstName">Nombre</label>
			<input type="text" id="firstName" name="firstName" placeholder="Nombre" required>
			<br>
			
			<label for="lastName">Apellidos</label>
			<input type="text" id="lastName" name="lastName" placeholder="Apellidos" required>
			<br>
			
			<label for="isSpecialEducator">Educador especial</label>
			<input type="checkbox" id="isSpecialEducator" name="isSpecialEducator" value="true">
			<br>
			
			<input type="submit" value="Enviar">
		</form>
	</main>
</body>
</html>