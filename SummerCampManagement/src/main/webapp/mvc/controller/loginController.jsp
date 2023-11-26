<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="business.dtos.UserDTO,data.memory.daos.InMemoryUserDAO" %>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<%
String nextPage = "../../index.jsp";
String mensajeNextPage = "";
if (customerBean == null || customerBean.getEmailUser().equals("")) {
	String emailUser = request.getParameter("email");
	String passwordUser = request.getParameter("password");

	if (emailUser != null && passwordUser != null) {
		InMemoryUserDAO userDAO = new InMemoryUserDAO();
		UserDTO user = userDAO.find(emailUser);

		if (user != null && user.getEmail().equalsIgnoreCase(emailUser) && user.getPassword().equals(passwordUser)) {
			customerBean.setEmailUser(emailUser);
			customerBean.setPasswordUser(passwordUser);
			customerBean.setRoleUser(user.getRole().name());
		} else {
			nextPage = "../view/loginView.jsp";
			mensajeNextPage = "El usuario que ha indicado no existe o no es v&aacute;lido";
		}
	} else {
		nextPage = "../view/loginView.jsp";		
	}
}
%>

<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=mensajeNextPage%>" name="message"/>
</jsp:forward>