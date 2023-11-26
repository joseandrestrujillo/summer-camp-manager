<%@page import="business.exceptions.dao.NotFoundException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="business.dtos.UserDTO,data.memory.daos.InMemoryUserDAO" %>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<%
String nextPage = "../../index.jsp";
String mensajeNextPage = "";
boolean error = false;
if (customerBean == null || customerBean.getEmailUser().equals("")) {
	String emailUser = request.getParameter("email");
	String passwordUser = request.getParameter("password");

	if (emailUser != null && passwordUser != null) {
		InMemoryUserDAO userDAO = new InMemoryUserDAO();

		try {
			UserDTO user = userDAO.find(emailUser);
			if (user.getEmail().equalsIgnoreCase(emailUser) && user.getPassword().equals(passwordUser)) {
				customerBean.setEmailUser(emailUser);
				customerBean.setPasswordUser(passwordUser);
				customerBean.setRoleUser(user.getRole().name());
				response.sendRedirect(nextPage);
			} else {
				mensajeNextPage = "Credenciales incorrectas";
				error = true;
			}
		} catch (NotFoundException e) {			
			mensajeNextPage = "El usuario que ha indicado no existe";
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