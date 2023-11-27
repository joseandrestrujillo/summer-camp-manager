<%@page import="business.exceptions.user.RegisterException"%>
<%@page import="business.exceptions.user.UserAlreadyExistsException"%>
<%@page import="business.exceptions.user.InvalidRoleException"%>
<%@page import="business.exceptions.dao.NotFoundException"%>
<%@page import="business.enums.UserRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="business.dtos.UserDTO,display.web.Container" %>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<%
String nextPage = "../../index.jsp";
String mensajeNextPage = "";
boolean error = false;
if (customerBean == null || customerBean.getEmailUser().equals("")) {
	String emailUser = request.getParameter("email");
	String passwordUser = request.getParameter("password");
	String roleString = request.getParameter("role");
	
	if (emailUser != null && passwordUser != null && roleString != null) {
		try {
			Container.getInstance().getUserManager().registerUser(emailUser, passwordUser, roleString);
			customerBean.setEmailUser(emailUser);
			customerBean.setPasswordUser(passwordUser);
			customerBean.setRoleUser(roleString);
			response.sendRedirect(nextPage);			
		}catch (InvalidRoleException e) {
			mensajeNextPage = "El role proporcionado es invalido.";
			error = true;
		}catch (UserAlreadyExistsException e) {
			mensajeNextPage = "Ya existe un usuario con ese email.";
			error = true;
		}catch (RegisterException e) {
			mensajeNextPage = "Error al registrar";
			error = true;
		}
	}
}

if ((customerBean == null || customerBean.getEmailUser().equals("")) || (error)) {
%>

<jsp:forward page="../view/registerView.jsp">
	<jsp:param value="<%=mensajeNextPage%>" name="message"/>
</jsp:forward>

<%
}
%>