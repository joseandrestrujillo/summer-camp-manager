<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%

String dniAttribute = request.getParameter("dniAttribute");
String birthDateAttribute = request.getParameter("birthDateAttribute");
String requireSpecialAttentionAttributes = request.getParameter("requireSpecialAttentionAttributes");

String firstNameValue = request.getParameter("firstNameValue");
firstNameValue = firstNameValue == null ? "" : "value='" + firstNameValue + "'";

String lastNameValue = request.getParameter("lastNameValue");
lastNameValue = lastNameValue == null ? "" : "value='" + lastNameValue + "'";

String dniValue = request.getParameter("dniValue");
dniValue = dniValue == null ? "" : "value='" + dniValue + "'";

String birthDateValue = request.getParameter("birthDateValue");

birthDateValue = birthDateValue == null ? "" : "value=\"" + birthDateValue + "\"";

%>

<label for="dni">DNI</label>
<input type="text" id="dni" name="dni" pattern="[0-9]{8}[A-Z]" <%= dniAttribute %> <%= dniValue %>>
<br>
<label for="firstName">Nombre</label>
<input type="text" id="firstName" name="firstName" placeholder="Nombre" required <%= firstNameValue %>>
<br>
<label for="lastName">Apellidos</label>
<input type="text" id="lastName" name="lastName" placeholder="Apellidos" required <%= lastNameValue %>>
<br>
<label for="birthDate">Fecha de nacimiento</label>
<input type="date" id="birthDate" name="birthDate" <%= birthDateAttribute %> <%= birthDateValue %>>
<br>
<label for="requireSpecialAttention">Requiere atención especial</label>
<input type="checkbox" id="requireSpecialAttention" name="requireSpecialAttention" value="true" <%= requireSpecialAttentionAttributes %>>
<br>
