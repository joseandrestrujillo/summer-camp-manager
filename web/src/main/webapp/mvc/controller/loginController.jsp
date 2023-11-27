<%@page import="business.exceptions.user.WrongCredentialsException"%>
<%@page import="business.exceptions.dao.NotFoundException"%>
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

	if (emailUser != null && passwordUser != null) {
		try {
				UserDTO user = Container.getInstance().getUserManager().verifyCredentials(emailUser, passwordUser);
				customerBean.setEmailUser(emailUser);
				customerBean.setPasswordUser(passwordUser);
				customerBean.setRoleUser(user.getRole().name());
				response.sendRedirect(nextPage);
		} catch (WrongCredentialsException e) {			
			mensajeNextPage = "El email o la contraseña son incorrectos";
			error = true;
		}

	}
}

if ((customerBean == null || customerBean.getEmailUser().equals("")) || (error)) {
%>

<jsp:forward page="../view/loginView.jsp">
	<jsp:param value="<%=mensajeNextPage%>" name="message"/>
</jsp:forward>

<%
}
%>