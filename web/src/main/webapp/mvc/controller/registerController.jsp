<%@page import="business.exceptions.dao.NotFoundException"%>
<%@page import="business.enums.UserRole"%>
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
	String roleString = request.getParameter("role");
	
	if (emailUser != null && passwordUser != null && roleString != null) {
		UserRole roleUser = UserRole.valueOf(roleString);
		InMemoryUserDAO userDAO = new InMemoryUserDAO();
		UserDTO user;		
		
		try {
			user = userDAO.find(emailUser);
			mensajeNextPage = "El usuario que ha indicado ya esta registrado";
			error = true;
		}catch (NotFoundException e) {
			try {
				userDAO.save(new UserDTO(emailUser, passwordUser, roleUser));		
				customerBean.setEmailUser(emailUser);
				customerBean.setPasswordUser(passwordUser);
				customerBean.setRoleUser(roleUser.name());
				response.sendRedirect(nextPage);
			} catch (RuntimeException ev) {
				mensajeNextPage = "Error en el sistema.";
				error = true;
			}
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