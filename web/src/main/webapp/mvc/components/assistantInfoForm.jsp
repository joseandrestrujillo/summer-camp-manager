<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%

String formAction = request.getParameter("formAction");

%>


<form action="<%=formAction%>" method="post">
    <label for="dni">DNI</label>
    <input type="text" id="dni" name="dni" pattern="[0-9]{8}[A-Z]" required>
    <br>
    <label for="firstName">Nombre</label>
    <input type="text" id="firstName" name="firstName" placeholder="Nombre" required>
    <br>
    <label for="lastName">Apellidos</label>
    <input type="text" id="lastName" name="lastName" placeholder="Apellidos" required>
    <br>
    <label for="birthDate">Fecha de nacimiento</label>
    <input type="date" id="birthDate" name="birthDate" required>
    <br>
    <label for="requireSpecialAttention">Requiere atención especial</label>
    <input type="checkbox" id="requireSpecialAttention" name="requireSpecialAttention" value="true">
    <br>
   	<button type="submit">Enviar</button>
</form>